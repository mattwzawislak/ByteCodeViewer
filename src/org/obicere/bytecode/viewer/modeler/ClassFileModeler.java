package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.AttributeSet;
import org.obicere.bytecode.core.objects.BootstrapMethodsAttribute;
import org.obicere.bytecode.core.objects.ClassFile;
import org.obicere.bytecode.core.objects.Constant;
import org.obicere.bytecode.core.objects.ConstantClass;
import org.obicere.bytecode.core.objects.ConstantPool;
import org.obicere.bytecode.core.objects.Field;
import org.obicere.bytecode.core.objects.InnerClass;
import org.obicere.bytecode.core.objects.InnerClassesAttribute;
import org.obicere.bytecode.core.objects.Method;
import org.obicere.bytecode.core.objects.RuntimeInvisibleAnnotationsAttribute;
import org.obicere.bytecode.core.objects.RuntimeInvisibleTypeAnnotationsAttribute;
import org.obicere.bytecode.core.objects.RuntimeVisibleAnnotationsAttribute;
import org.obicere.bytecode.core.objects.RuntimeVisibleTypeAnnotationsAttribute;
import org.obicere.bytecode.core.objects.SignatureAttribute;
import org.obicere.bytecode.core.objects.SyntheticAttribute;
import org.obicere.bytecode.core.objects.signature.ClassSignature;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;
import org.obicere.bytecode.viewer.util.ByteCodeUtils;

import java.util.Set;
import java.util.TreeSet;

/**
 */
public class ClassFileModeler implements Modeler<ClassFile> {

    @Override
    public void model(final ClassFile element, final DocumentBuilder builder) {

        // we use this override for InnerClass attributes to set the proper access flags
        final int accessFlags;
        final boolean innerClass;
        final Object newAccessFlags = builder.getProperty("accessFlags");
        if (newAccessFlags != null) {
            accessFlags = (int) newAccessFlags;
            innerClass = true;
        } else {
            accessFlags = element.getAccessFlags();
            innerClass = false;
        }
        builder.openBlock();

        modelConstantPool(element, builder);

        // only model version and imports for outer classes
        if (!innerClass) {
            modelImports(element, builder);
            modelVersion(element, builder);
        }

        final AttributeSet attributes = element.getAttributeSet();
        if (ByteCodeUtils.isSynthetic(accessFlags) || attributes.getAttribute(SyntheticAttribute.class) != null) {
            addSynthetic(builder);
        }

        modelAnnotations(element, builder);
        modelClassDeclaration(element, builder, accessFlags);

        builder.indent();

        modelFields(element, builder);
        modelMethods(element, builder);
        modelBootstrapMethods(element, builder);
        modelInnerClasses(element, builder);

        builder.unindent();

        builder.newLine();
        builder.add("}");
        builder.closeBlock();
    }

    private void modelConstantPool(final ClassFile element, final DocumentBuilder builder) {
        final ConstantPool constantPool = element.getConstantPool();
        final boolean display = builder.getDomain().getSettingsController().getSettings().getBoolean("code.includeConstantPool");
        if (!display) {
            return;
        }
        builder.model(constantPool);
    }

    private void modelImports(final ClassFile element, final DocumentBuilder builder) {
        final boolean display = builder.getDomain().getSettingsController().getSettings().getBoolean("code.importMode");
        if (!display) {
            return;
        }
        final ConstantPool constantPool = element.getConstantPool();
        final AttributeSet attributes = element.getAttributeSet();

        final String name = element.getName();
        final String thisPackage = ByteCodeUtils.getPackage(name);

        final Set<String> imports = new TreeSet<>();
        getImports(constantPool, imports, thisPackage);

        final InnerClassesAttribute innerClassesAttribute = attributes.getAttribute(InnerClassesAttribute.class);
        if (innerClassesAttribute != null) {
            for (final InnerClass innerClass : innerClassesAttribute.getInnerClasses()) {
                final ClassFile file = builder.getClassInformation().getClass(constantPool.getAsString(innerClass.getInnerClassInfoIndex()));
                if (file == null) {
                    continue;
                }
                getImports(file.getConstantPool(), imports, thisPackage);
            }
        }

        if (!thisPackage.equals("")) {
            builder.addKeyword("package ");
            builder.add(thisPackage);
            builder.add(";");
            builder.newLine();
            builder.newLine();
        }

        for (final String next : imports) {
            builder.addKeyword("import ");
            builder.add(next);
            builder.add(";");
            builder.newLine();
        }
        builder.newLine();
    }

    private void getImports(final ConstantPool constantPool, final Set<String> imports, final String thisPackage) {

        for (final Constant constant : constantPool.getConstants()) {
            if (constant instanceof ConstantClass) {
                final ConstantClass constantClass = (ConstantClass) constant;
                final String name = constantClass.toString(constantPool);

                // arrays are not imported
                if (name.startsWith("[")) {
                    continue;
                }

                final String nextPackage = ByteCodeUtils.getPackage(name);
                // check to see if they are both null or both equal
                // and also exclude java.lang packages
                if (nextPackage.equals(thisPackage) || nextPackage.equals("java.lang")) {
                    continue;
                }

                imports.add(ByteCodeUtils.getQualifiedName(name));
            }
        }
    }

    private void addSynthetic(final DocumentBuilder builder) {
        builder.addComment("Synthetic Class");
        builder.newLine();
    }

    private void modelAnnotations(final ClassFile element, final DocumentBuilder builder) {
        final AttributeSet attributes = element.getAttributeSet();

        final Set<RuntimeVisibleAnnotationsAttribute> rvaAttributes = attributes.getAttributes(RuntimeVisibleAnnotationsAttribute.class);
        final Set<RuntimeInvisibleAnnotationsAttribute> riaAttributes = attributes.getAttributes(RuntimeInvisibleAnnotationsAttribute.class);

        if (rvaAttributes != null) {
            rvaAttributes.forEach(e -> {
                builder.model(e);
                builder.newLine();
            });
        }
        if (riaAttributes != null) {
            riaAttributes.forEach(e -> {
                builder.model(e);
                builder.newLine();
            });
        }
    }

    private void modelVersion(final ClassFile element, final DocumentBuilder builder) {
        builder.add("Major: ");
        builder.add(element.getMajorVersion());
        builder.add(" Minor: ");
        builder.add(element.getMinorVersion());
        builder.newLine();
        builder.newLine();
    }

    private void modelClassDeclaration(final ClassFile element, final DocumentBuilder builder, final int accessFlags) {

        final String className = element.getName();
        final String[] names = ByteCodeUtils.getClassAccessNames(accessFlags);
        final ConstantPool constantPool = element.getConstantPool();
        final AttributeSet attributes = element.getAttributeSet();

        for (final String name : names) {
            builder.addKeyword(name);
            builder.pad(1);
        }

        final boolean importMode = builder.getDomain().getSettingsController().getSettings().getBoolean("code.importMode");
        if (importMode) {
            builder.add(ByteCodeUtils.getClassName(className));
        } else {
            builder.add(ByteCodeUtils.getQualifiedName(className));
        }

        final Set<SignatureAttribute> signatures = attributes.getAttributes(SignatureAttribute.class);
        final ClassSignature signature;
        if (signatures != null && !signatures.isEmpty()) {
            final SignatureAttribute attribute = signatures.iterator().next();
            signature = attribute.parseClass(constantPool);
        } else {
            final StringBuilder newSignature = new StringBuilder();
            newSignature.append('L');
            newSignature.append(element.getSuperName());
            newSignature.append(';');
            final int[] interfaces = element.getInterfaces();
            for (final int interfaceIndex : interfaces) {
                final String name = constantPool.getAsString(interfaceIndex);
                newSignature.append('L');
                newSignature.append(name);
                newSignature.append(';');
            }
            signature = SignatureAttribute.parseClass(newSignature.toString());
        }

        final Set<RuntimeVisibleTypeAnnotationsAttribute> rvtaAttributes = attributes.getAttributes(RuntimeVisibleTypeAnnotationsAttribute.class);
        final Set<RuntimeInvisibleTypeAnnotationsAttribute> ritaAttributes = attributes.getAttributes(RuntimeInvisibleTypeAnnotationsAttribute.class);

        if (rvtaAttributes != null) {
            rvtaAttributes.forEach(e -> signature.addAnnotations(e.getAnnotations()));
        }
        if (ritaAttributes != null) {
            ritaAttributes.forEach(e -> signature.addAnnotations(e.getAnnotations()));
        }

        if (ByteCodeUtils.isInterface(accessFlags)) {
            // TODO resolve this conflict
            //signature.modelInterface(builder);
        } else {
            //signature.modelClass(builder);
        }

        builder.add(" {");
    }

    private void modelFields(final ClassFile element, final DocumentBuilder builder) {
        final Field[] fields = element.getFields();
        if (fields.length == 0) {
            return;
        }
        for (final Field field : fields) {
            builder.newLine();
            builder.newLine();

            builder.model(field);
        }
    }

    private void modelMethods(final ClassFile element, final DocumentBuilder builder) {
        final Method[] methods = element.getMethods();
        if (methods.length == 0) {
            return;
        }
        for (final Method method : methods) {
            builder.newLine();
            builder.newLine();

            builder.model(method);
        }
    }

    private void modelInnerClasses(final ClassFile element, final DocumentBuilder builder) {

        final ConstantPool constantPool = element.getConstantPool();
        final AttributeSet attributeSet = element.getAttributeSet();
        final String className = element.getName();

        final Set<InnerClassesAttribute> attributes = attributeSet.getAttributes(InnerClassesAttribute.class);
        if (attributes == null) {
            return;
        }
        for (final InnerClassesAttribute attribute : attributes) {
            final InnerClass[] innerClasses = attribute.getInnerClasses();
            for (final InnerClass innerClass : innerClasses) {
                final int innerIndex = innerClass.getInnerClassInfoIndex();
                final int outerIndex = innerClass.getOuterClassInfoIndex();

                final String name = constantPool.getAsString(innerIndex);
                final String outer = constantPool.getAsString(outerIndex);

                if (name.equals(className)) {
                    continue;
                }
                if (!outer.equals("<null entry>") && !className.equals(outer)) {
                    continue;
                }

                builder.newLine();
                builder.model(innerClass);
            }
        }
    }

    private void modelBootstrapMethods(final ClassFile element, final DocumentBuilder builder) {
        final AttributeSet attributeSet = element.getAttributeSet();

        final BootstrapMethodsAttribute attribute = attributeSet.getAttribute(BootstrapMethodsAttribute.class);

        if (attribute == null) {
            return;
        }
        builder.model(attribute);
    }
}

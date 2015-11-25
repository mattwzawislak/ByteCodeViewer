package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.bytecode.signature.ClassSignature;
import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.util.BytecodeUtils;

import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Obicere
 */
public class ClassFile extends BytecodeElement {

    private final int minorVersion;

    private final int majorVersion;

    private final ConstantPool constantPool;

    private final int accessFlags;

    private final int thisClass;

    private final int superClass;

    private final int[] interfaces;

    private final Field[] fields;

    private final Method[] methods;

    private final Attribute[] attributes;

    private final AttributeSet attributeSet;

    public ClassFile(final int minorVersion, final int majorVersion, final ConstantPool constantPool, final int accessFlags, final int thisClass, final int superClass, final int[] interfaces, final Field[] fields, final Method[] methods, final Attribute[] attributes) {
        this.minorVersion = minorVersion;
        this.majorVersion = majorVersion;
        this.constantPool = constantPool;
        this.accessFlags = accessFlags;
        this.thisClass = thisClass;
        this.superClass = superClass;
        this.interfaces = interfaces;
        this.fields = fields;
        this.methods = methods;
        this.attributes = attributes;
        this.attributeSet = new AttributeSet(attributes);
    }

    public int getMinorVersion() {
        return minorVersion;
    }

    public int getMajorVersion() {
        return majorVersion;
    }

    public ConstantPool getConstantPool() {
        return constantPool;
    }

    public int getAccessFlags() {
        return accessFlags;
    }

    public int getSuperClass() {
        return superClass;
    }

    public String getSuperName() {
        return constantPool.getAsString(superClass);
    }

    public int getThisClass() {
        return thisClass;
    }

    public String getName() {
        return constantPool.getAsString(thisClass);
    }

    public int[] getInterfaces() {
        return interfaces;
    }

    public String getInterface(final int index) {
        return constantPool.getAsString(index);
    }

    public Field[] getFields() {
        return fields;
    }

    public Method[] getMethods() {
        return methods;
    }

    public Attribute[] getAttributes() {
        return attributes;
    }

    // EnclosingMethod - Still not fully implemented, the parsing is quite off
    // basically, the information is stored within itself within an inner class
    // attribute

    @Override
    public void model(final DocumentBuilder builder) {

        // we use this override for InnerClass attributes to set the proper access flags
        final int accessFlags;
        final boolean innerClass;
        final Object newAccessFlags = builder.getProperty("accessFlags");
        if (newAccessFlags != null) {
            accessFlags = (int) newAccessFlags;
            innerClass = true;
        } else {
            accessFlags = getAccessFlags();
            innerClass = false;
        }
        builder.openBlock();

        modelConstantPool(builder);

        // only model version and imports for outer classes
        if (!innerClass) {
            modelImports(builder);
            modelVersion(builder);
        }

        if (BytecodeUtils.isSynthetic(accessFlags) || attributeSet.getAttribute(SyntheticAttribute.class) != null) {
            addSynthetic(builder);
        }

        modelAnnotations(builder);
        modelClassDeclaration(builder, accessFlags);

        builder.indent();

        modelFields(builder);
        modelMethods(builder);
        modelBootstrapMethods(builder);
        modelInnerClasses(builder);

        builder.unindent();

        builder.newLine();
        builder.add("}");
        builder.closeBlock();
    }

    private void modelConstantPool(final DocumentBuilder builder) {
        final boolean display = builder.getDomain().getSettingsController().getSettings().getBoolean("code.includeConstantPool");
        if (!display) {
            return;
        }
        constantPool.model(builder);
    }

    private void modelImports(final DocumentBuilder builder) {
        final boolean display = builder.getDomain().getSettingsController().getSettings().getBoolean("code.importMode");
        if (!display) {
            return;
        }

        final String name = getName();
        final String packageName = BytecodeUtils.getPackage(name);
        final String thisName = BytecodeUtils.getQualifiedName(name);
        final int lastDot = thisName.lastIndexOf('.');

        final String thisPackage;
        if (lastDot > 0) {
            thisPackage = thisName.substring(0, lastDot);
        } else {
            thisPackage = null;
        }
        final Set<String> imports = new TreeSet<>();
        getImports(constantPool, imports, thisPackage);

        final InnerClassesAttribute innerClassesAttribute = attributeSet.getAttribute(InnerClassesAttribute.class);
        if (innerClassesAttribute != null) {
            for (final InnerClass innerClass : innerClassesAttribute.getInnerClasses()) {
                final ClassFile file = builder.getClassInformation().getClass(constantPool.getAsString(innerClass.getInnerClassInfoIndex()));
                if (file == null) {
                    continue;
                }
                getImports(file.getConstantPool(), imports, thisPackage);
            }
        }

        if (packageName != null) {
            builder.addKeyword("package ");
            builder.add(packageName);
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

                final String nextPackage = BytecodeUtils.getPackage(name);
                // check to see if they are both null or both equal
                // and also exclude java.lang packages
                if (Objects.equals(nextPackage, thisPackage) || nextPackage != null && nextPackage.equals("java.lang")) {
                    continue;
                }

                imports.add(BytecodeUtils.getQualifiedName(name));
            }
        }
    }

    private void addSynthetic(final DocumentBuilder builder) {
        builder.addComment("Synthetic Class");
        builder.newLine();
    }

    private void modelAnnotations(final DocumentBuilder builder) {
        final Set<RuntimeVisibleAnnotationsAttribute> rvaAttributes = attributeSet.getAttributes(RuntimeVisibleAnnotationsAttribute.class);
        final Set<RuntimeInvisibleAnnotationsAttribute> riaAttributes = attributeSet.getAttributes(RuntimeInvisibleAnnotationsAttribute.class);

        if (rvaAttributes != null) {
            rvaAttributes.forEach(e -> {
                e.model(builder);
                builder.newLine();
            });
        }
        if (riaAttributes != null) {
            riaAttributes.forEach(e -> {
                e.model(builder);
                builder.newLine();
            });
        }
    }

    private void modelVersion(final DocumentBuilder builder) {
        builder.add("Major: ");
        builder.add(majorVersion);
        builder.add(" Minor: ");
        builder.add(minorVersion);
        builder.newLine();
        builder.newLine();
    }

    private void modelClassDeclaration(final DocumentBuilder builder, final int accessFlags) {

        final String[] names = BytecodeUtils.getClassAccessNames(accessFlags);

        for (final String name : names) {
            builder.addKeyword(name);
            builder.pad(1);
        }

        final boolean importMode = builder.getDomain().getSettingsController().getSettings().getBoolean("code.importMode");
        if (importMode) {
            builder.add(BytecodeUtils.getClassName(getName()));
        } else {
            builder.add(BytecodeUtils.getQualifiedName(getName()));
        }

        final Set<SignatureAttribute> signatures = attributeSet.getAttributes(SignatureAttribute.class);
        final ClassSignature signature;
        if (signatures != null && !signatures.isEmpty()) {
            final SignatureAttribute attribute = signatures.iterator().next();
            signature = attribute.parseClass(constantPool);
        } else {
            final StringBuilder newSignature = new StringBuilder();
            newSignature.append('L');
            newSignature.append(getSuperName());
            newSignature.append(';');
            final int[] interfaces = getInterfaces();
            for (final int interfaceIndex : interfaces) {
                final String name = constantPool.getAsString(interfaceIndex);
                newSignature.append('L');
                newSignature.append(name);
                newSignature.append(';');
            }
            signature = SignatureAttribute.parseClass(newSignature.toString());
        }

        final Set<RuntimeVisibleTypeAnnotationsAttribute> rvtaAttributes = attributeSet.getAttributes(RuntimeVisibleTypeAnnotationsAttribute.class);
        final Set<RuntimeInvisibleTypeAnnotationsAttribute> ritaAttributes = attributeSet.getAttributes(RuntimeInvisibleTypeAnnotationsAttribute.class);

        if (rvtaAttributes != null) {
            rvtaAttributes.forEach(e -> signature.addAnnotations(e.getAnnotations()));
        }
        if (ritaAttributes != null) {
            ritaAttributes.forEach(e -> signature.addAnnotations(e.getAnnotations()));
        }

        if (BytecodeUtils.isInterface(accessFlags)) {
            signature.modelInterface(builder);
        } else {
            signature.modelClass(builder);
        }

        builder.add(" {");
    }

    private void modelFields(final DocumentBuilder builder) {
        final Field[] fields = getFields();
        if (fields.length == 0) {
            return;
        }
        for (final Field field : fields) {
            builder.newLine();
            builder.newLine();

            field.model(builder);
        }
    }

    private void modelMethods(final DocumentBuilder builder) {
        final Method[] methods = getMethods();
        if (methods.length == 0) {
            return;
        }
        for (final Method method : methods) {
            builder.newLine();
            builder.newLine();

            method.model(builder);
        }
    }

    private void modelInnerClasses(final DocumentBuilder builder) {
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

                if (name.equals(getName())) {
                    continue;
                }
                if (!outer.equals("<null entry>") && !getName().equals(outer)) {
                    continue;
                }

                builder.newLine();
                innerClass.model(builder);
            }
        }
    }

    private void modelBootstrapMethods(final DocumentBuilder builder) {

        final BootstrapMethodsAttribute attribute = attributeSet.getAttribute(BootstrapMethodsAttribute.class);

        if (attribute == null) {
            return;
        }
        attribute.model(builder);
    }
}

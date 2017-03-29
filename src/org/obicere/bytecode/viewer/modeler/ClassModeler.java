package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.DefaultJCClass;
import org.obicere.bytecode.core.objects.DefaultJCField;
import org.obicere.bytecode.core.objects.DefaultJCMethod;
import org.obicere.bytecode.core.objects.attribute.DefaultAttributeSet;
import org.obicere.bytecode.core.objects.attribute.BootstrapMethodsAttribute;
import org.obicere.bytecode.core.objects.constant.ConstantPool;
import org.obicere.bytecode.core.objects.common.InnerClass;
import org.obicere.bytecode.core.objects.attribute.InnerClassesAttribute;
import org.obicere.bytecode.core.objects.attribute.RuntimeInvisibleAnnotationsAttribute;
import org.obicere.bytecode.core.objects.attribute.RuntimeInvisibleTypeAnnotationsAttribute;
import org.obicere.bytecode.core.objects.attribute.RuntimeVisibleAnnotationsAttribute;
import org.obicere.bytecode.core.objects.attribute.RuntimeVisibleTypeAnnotationsAttribute;
import org.obicere.bytecode.core.objects.attribute.SignatureAttribute;
import org.obicere.bytecode.core.objects.attribute.SyntheticAttribute;
import org.obicere.bytecode.core.objects.signature.ClassSignature;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;
import org.obicere.bytecode.viewer.util.ByteCodeUtils;

import java.util.Set;

/**
 */
public class ClassModeler implements Modeler<DefaultJCClass> {

    @Override
    public void model(final DefaultJCClass element, final DocumentBuilder builder) {

        // we use this override for InnerClass attributes to set the proper access flags
        final int accessFlags;
        final Object newAccessFlags = builder.getProperty("accessFlags");
        if (newAccessFlags != null) {
            accessFlags = (int) newAccessFlags;
        } else {
            accessFlags = element.getAccessFlags();
        }
        builder.openBlock();

        final DefaultAttributeSet attributes = element.getAttributeSet();
        if (ByteCodeUtils.isSynthetic(accessFlags) || attributes.getAttribute(SyntheticAttribute.class) != null) {
            addSynthetic(builder);
        }

        modelVersion(element, builder);
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

    private void addSynthetic(final DocumentBuilder builder) {
        builder.addComment("Synthetic Class");
        builder.newLine();
    }

    private void modelAnnotations(final DefaultJCClass element, final DocumentBuilder builder) {
        final DefaultAttributeSet attributes = element.getAttributeSet();

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

    private void modelVersion(final DefaultJCClass element, final DocumentBuilder builder) {
        builder.addComment("Version: " + element.getMajorVersion() + "." + element.getMinorVersion());
        builder.newLine();
    }

    private void modelClassDeclaration(final DefaultJCClass element, final DocumentBuilder builder, final int accessFlags) {

        final String className = element.getName();
        final String[] names = ByteCodeUtils.getClassAccessNames(accessFlags);
        final ConstantPool constantPool = element.getConstantPool();
        final DefaultAttributeSet attributes = element.getAttributeSet();

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

        final SignatureAttribute signatureAttribute = attributes.getAttribute(SignatureAttribute.class);
        final ClassSignature signature;
        if (signatureAttribute != null) {
            signature = signatureAttribute.getAsClassSignature(constantPool);
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
            signature = ClassSignature.parse(newSignature.toString());
        }

        final Set<RuntimeVisibleTypeAnnotationsAttribute> rvtaAttributes = attributes.getAttributes(RuntimeVisibleTypeAnnotationsAttribute.class);
        final Set<RuntimeInvisibleTypeAnnotationsAttribute> ritaAttributes = attributes.getAttributes(RuntimeInvisibleTypeAnnotationsAttribute.class);

        if (rvtaAttributes != null) {
            rvtaAttributes.forEach(e -> signature.addAnnotations(e.getAnnotations()));
        }
        if (ritaAttributes != null) {
            ritaAttributes.forEach(e -> signature.addAnnotations(e.getAnnotations()));
        }

        builder.model(signature);

        builder.add(" {");
    }

    private void modelFields(final DefaultJCClass element, final DocumentBuilder builder) {
        final DefaultJCField[] fields = element.getFields();
        if (fields.length == 0) {
            return;
        }
        for (final DefaultJCField field : fields) {
            builder.newLine();

            builder.model(field);
        }
    }

    private void modelMethods(final DefaultJCClass element, final DocumentBuilder builder) {
        final DefaultJCMethod[] methods = element.getMethods();
        if (methods.length == 0) {
            return;
        }
        for (final DefaultJCMethod method : methods) {
            builder.newLine();

            builder.model(method);
        }
    }

    private void modelInnerClasses(final DefaultJCClass element, final DocumentBuilder builder) {

        final ConstantPool constantPool = element.getConstantPool();
        final DefaultAttributeSet attributeSet = element.getAttributeSet();
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

    private void modelBootstrapMethods(final DefaultJCClass element, final DocumentBuilder builder) {
        final DefaultAttributeSet attributes = element.getAttributeSet();

        final BootstrapMethodsAttribute attribute = attributes.getAttribute(BootstrapMethodsAttribute.class);

        if (attribute == null) {
            return;
        }
        builder.model(attribute);
    }
}

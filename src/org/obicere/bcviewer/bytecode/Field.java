package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.bytecode.signature.FieldSignature;
import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.util.BytecodeUtils;

import java.util.Set;

/**
 * @author Obicere
 */
public class Field extends BytecodeElement {

    private final int accessFlags;

    private final int nameIndex;

    private final int descriptorIndex;

    private final Attribute[] attributes;

    private final AttributeSet attributeSet;

    public Field(final int accessFlags, final int nameIndex, final int descriptorIndex, final Attribute[] attributes) {
        this.accessFlags = accessFlags;
        this.nameIndex = nameIndex;
        this.descriptorIndex = descriptorIndex;
        this.attributes = attributes;
        this.attributeSet = new AttributeSet(attributes);
    }

    public int getAccessFlags() {
        return accessFlags;
    }

    public int getNameIndex() {
        return nameIndex;
    }

    public int getDescriptorIndex() {
        return descriptorIndex;
    }

    public Attribute[] getAttributes() {
        return attributes;
    }

    @Override
    public void model(final DocumentBuilder builder) {

        if (BytecodeUtils.isSynthetic(accessFlags) || attributeSet.getAttribute(SyntheticAttribute.class) != null) {
            addSynthetic(builder);
            builder.newLine();
        }

        modelAnnotations(builder);
        modelDeclaration(builder);
    }

    private void addSynthetic(final DocumentBuilder builder) {
        builder.addComment("Synthetic Field");
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

    private void modelType(final DocumentBuilder builder, final ConstantPool constantPool) {
        final SignatureAttribute attribute = attributeSet.getAttribute(SignatureAttribute.class);
        final FieldSignature signature;
        if (attribute != null) {
            signature = attribute.parseField(constantPool);
        } else {
            final String descriptor = constantPool.getAsString(descriptorIndex);
            signature = SignatureAttribute.parseField(descriptor);
        }

        if (signature != null) {

            // add type annotations to the signature now

            final Set<RuntimeVisibleTypeAnnotationsAttribute> rvtaAttributes = attributeSet.getAttributes(RuntimeVisibleTypeAnnotationsAttribute.class);
            final Set<RuntimeInvisibleTypeAnnotationsAttribute> ritaAttributes = attributeSet.getAttributes(RuntimeInvisibleTypeAnnotationsAttribute.class);

            if (rvtaAttributes != null) {
                rvtaAttributes.forEach(e -> signature.addAnnotations(e.getAnnotations()));
            }
            if (ritaAttributes != null) {
                ritaAttributes.forEach(e -> signature.addAnnotations(e.getAnnotations()));
            }

            signature.model(builder);
        }
    }

    private void modelDeclaration(final DocumentBuilder builder) {
        final ConstantPool constantPool = builder.getConstantPool();
        final String[] names = BytecodeUtils.getFieldAccessNames(accessFlags);

        for (final String name : names) {
            builder.addKeyword(name + " ");
        }

        modelType(builder, constantPool);

        builder.add(" " + constantPool.getAsString(nameIndex));

        final ConstantValueAttribute constantAttribute = attributeSet.getAttribute(ConstantValueAttribute.class);
        if (constantAttribute != null) {
            final Constant constant = constantPool.get(constantAttribute.getConstantValueIndex());
            builder.add(" = ");
            constant.modelValue(builder);
        }
        builder.add(";");
    }
}

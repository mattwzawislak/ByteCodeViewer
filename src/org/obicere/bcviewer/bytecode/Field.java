package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.bytecode.signature.FieldSignature;
import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;
import org.obicere.bcviewer.util.BytecodeUtils;

import javax.swing.text.Element;
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
    public void model(final BytecodeDocumentBuilder builder, final Element parent) {

        if (BytecodeUtils.isSynthetic(accessFlags) || attributeSet.getAttribute(SyntheticAttribute.class) != null) {
            addSynthetic(builder, parent);
            builder.newLine(parent);
        }

        modelAnnotations(builder, parent);
        modelDeclaration(builder, parent);
    }

    private void addSynthetic(final BytecodeDocumentBuilder builder, final Element parent) {
        builder.addComment(parent, "Synthetic Field");
    }

    private void modelAnnotations(final BytecodeDocumentBuilder builder, final Element parent) {
        final Set<RuntimeVisibleAnnotationsAttribute> rvaAttributes = attributeSet.getAttributes(RuntimeVisibleAnnotationsAttribute.class);
        final Set<RuntimeInvisibleAnnotationsAttribute> riaAttributes = attributeSet.getAttributes(RuntimeInvisibleAnnotationsAttribute.class);

        if (rvaAttributes != null) {
            rvaAttributes.forEach(e -> {
                e.model(builder, parent);
                builder.newLine(parent);
            });
        }
        if (riaAttributes != null) {
            riaAttributes.forEach(e -> {
                e.model(builder, parent);
                builder.newLine(parent);
            });
        }
    }

    private void modelType(final BytecodeDocumentBuilder builder, final Element parent, final ConstantPool constantPool) {
        final Set<SignatureAttribute> signatures = attributeSet.getAttributes(SignatureAttribute.class);
        final FieldSignature signature;
        if (signatures != null && !signatures.isEmpty()) {
            final SignatureAttribute attribute = signatures.iterator().next();
            signature = attribute.parseField(constantPool);
        } else {
            final String descriptor = constantPool.getAsString(descriptorIndex);
            signature = SignatureAttribute.parseField(descriptor);
        }

        // add type annotations to the signature now

        final Set<RuntimeVisibleTypeAnnotationsAttribute> rvtaAttributes = attributeSet.getAttributes(RuntimeVisibleTypeAnnotationsAttribute.class);
        final Set<RuntimeInvisibleTypeAnnotationsAttribute> ritaAttributes = attributeSet.getAttributes(RuntimeInvisibleTypeAnnotationsAttribute.class);

        if (rvtaAttributes != null) {
            rvtaAttributes.forEach(e -> signature.addAnnotations(e.getAnnotations()));
        }
        if (ritaAttributes != null) {
            ritaAttributes.forEach(e -> signature.addAnnotations(e.getAnnotations()));
        }

        signature.model(builder, parent);
    }

    private void modelDeclaration(final BytecodeDocumentBuilder builder, final Element parent) {
        final ConstantPool constantPool = builder.getConstantPool();
        final String[] names = BytecodeUtils.getFieldAccessNames(accessFlags);

        for (final String name : names) {
            builder.addPlain(parent, name + " ");
        }

        modelType(builder, parent, constantPool);

        builder.addPlain(parent, constantPool.getAsString(nameIndex));

        final ConstantValueAttribute constantAttribute = attributeSet.getAttribute(ConstantValueAttribute.class);
        if (constantAttribute != null) {
            final Constant constant = constantPool.get(constantAttribute.getConstantValueIndex());
            builder.addPlain(parent, " = ");
            constant.modelValue(builder, parent);
        }
        builder.addPlain(parent, ";");
    }
}

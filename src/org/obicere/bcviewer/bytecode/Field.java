package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.bytecode.signature.FieldSignature;
import org.obicere.bcviewer.dom.BasicElement;
import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.Element;
import org.obicere.bcviewer.dom.TextElement;
import org.obicere.bcviewer.dom.literals.CommentElement;
import org.obicere.bcviewer.dom.literals.KeywordElement;
import org.obicere.bcviewer.dom.literals.PlainElement;
import org.obicere.bcviewer.util.BytecodeUtils;

import java.util.LinkedList;
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
    public void model(final DocumentBuilder builder, final Element parent) {
        final Element fieldElement = new BasicElement(getIdentifier());

        if (BytecodeUtils.isSynthetic(accessFlags)) {
            addSynthetic(builder, fieldElement);
        } else {
            final Set<SyntheticAttribute> syntheticAttributes = attributeSet.getAttributes(SyntheticAttribute.class);
            if (syntheticAttributes != null && !syntheticAttributes.isEmpty()) {
                // there is at least 1 synthetic - we should set it
                addSynthetic(builder, fieldElement);
            }
        }

        modelAnnotations(builder, fieldElement);
        modelDeclaration(builder, fieldElement);
        parent.add(fieldElement);
    }

    private void addSynthetic(final DocumentBuilder builder, final Element parent) {
        parent.add(new CommentElement("synthetic", "Synthetic Field", builder));
    }

    private void modelAnnotations(final DocumentBuilder builder, final Element parent) {
        final Set<RuntimeVisibleAnnotationsAttribute> rvaAttributes = attributeSet.getAttributes(RuntimeVisibleAnnotationsAttribute.class);
        final Set<RuntimeInvisibleAnnotationsAttribute> riaAttributes = attributeSet.getAttributes(RuntimeInvisibleAnnotationsAttribute.class);

        if (rvaAttributes != null) {
            rvaAttributes.forEach(e -> e.model(builder, parent));
        }
        if (riaAttributes != null) {
            riaAttributes.forEach(e -> e.model(builder, parent));
        }
    }

    private void modelType(final DocumentBuilder builder, final Element parent, final ConstantPool constantPool) {
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

    private void modelDeclaration(final DocumentBuilder builder, final Element parent) {
        final ConstantPool constantPool = builder.getConstantPool();
        final String[] names = BytecodeUtils.getFieldAccessNames(accessFlags);

        final Element declaration = new BasicElement("declaration", Element.AXIS_LINE);
        for (final String name : names) {
            final TextElement next = new KeywordElement(name, name, builder);
            next.setRightPad(1);
            declaration.add(next);
        }

        modelType(builder, declaration, constantPool);

        final TextElement element = new PlainElement("name", constantPool.getAsString(nameIndex), builder);
        declaration.add(element);

        for (final Attribute attribute : attributes) {
            if (attribute instanceof ConstantValueAttribute) {
                final PlainElement equals = new PlainElement("equals", "=", builder);
                equals.setLeftPad(1);
                equals.setRightPad(1);
                final ConstantValueAttribute constant = (ConstantValueAttribute) attribute;
                final TextElement constantElement = new PlainElement("constant", constantPool.getAsString(constant.getConstantValueIndex()), builder);

                declaration.add(equals);
                declaration.add(constantElement);
                break;
            }
        }

        declaration.add(new PlainElement("semicolon", ";", builder));

        parent.add(declaration);
    }
}

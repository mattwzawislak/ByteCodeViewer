package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.Element;
import org.obicere.bcviewer.dom.TextElement;
import org.obicere.bcviewer.dom.literals.KeywordElement;
import org.obicere.bcviewer.dom.literals.PlainElement;
import org.obicere.bcviewer.util.BytecodeUtils;

import java.util.LinkedList;

/**
 * @author Obicere
 */
public class Field extends BytecodeElement {

    private final int accessFlags;

    private final int nameIndex;

    private final int descriptorIndex;

    private final Attribute[] attributes;

    public Field(final int accessFlags, final int nameIndex, final int descriptorIndex, final Attribute[] attributes) {
        this.accessFlags = accessFlags;
        this.nameIndex = nameIndex;
        this.descriptorIndex = descriptorIndex;
        this.attributes = attributes;
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

    // TODO: ConstantValue
    // TODO: Synthetic
    // TODO: Signature
    // TODO: Deprecated
    // TODO: RuntimeVisibleAnnotations
    // TODO: RuntimeInvisibleAnnotations

    @Override
    public void model(final DocumentBuilder builder, final Element parent) {
        final ConstantPool constantPool = builder.getConstantPool();

        final String[] names = BytecodeUtils.getFieldAccessNames(accessFlags);

        for (final String name : names) {
            final TextElement next = new KeywordElement(name, name, builder);
            next.setRightPad(1);
            parent.add(next);
        }

        modelType(builder, parent, constantPool);

        final TextElement element = new PlainElement("name", constantPool.getAsString(nameIndex) + ";", builder);
        element.setLeftPad(1);
        parent.add(element);
    }

    private void modelType(final DocumentBuilder builder, final Element parent, final ConstantPool constantPool) {
        final String descriptor = constantPool.getAsString(descriptorIndex);
        final LinkedList<Element> arrays = new LinkedList<>();
        int i = 0;
        while (descriptor.charAt(i) == '[') {
            arrays.add(new PlainElement("array" + i, "[]", builder));
            i++;
        }
        final TextElement typeDescriptor = getDescriptor(i, descriptor, builder);
        parent.add(typeDescriptor);

        arrays.forEach(parent::add);
    }

    private TextElement getDescriptor(final int index, final String str, final DocumentBuilder builder) {
        switch (str.charAt(index)) {
            case 'B':
                return new KeywordElement("type", "byte", builder);
            case 'C':
                return new KeywordElement("type", "char", builder);
            case 'D':
                return new KeywordElement("type", "double", builder);
            case 'F':
                return new KeywordElement("type", "float", builder);
            case 'I':
                return new KeywordElement("type", "int", builder);
            case 'J':
                return new KeywordElement("type", "long", builder);
            case 'L':
                // +1 to skip the descriptor character
                // -1 to skip the trailing ';' character
                final String type = str.substring(index + 1, str.length() - 1);
                return new PlainElement("type", BytecodeUtils.getQualifiedName(type), builder);
            case 'S':
                return new KeywordElement("type", "short", builder);
            case 'Z':
                return new KeywordElement("type", "boolean", builder);
            default:
                throw new AssertionError("illegal type descriptor: " + str.charAt(index));
        }
    }
}

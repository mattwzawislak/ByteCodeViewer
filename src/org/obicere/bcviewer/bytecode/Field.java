package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.Element;
import org.obicere.bcviewer.dom.TextElement;
import org.obicere.bcviewer.dom.literals.KeywordElement;
import org.obicere.bcviewer.dom.literals.PlainElement;
import org.obicere.bcviewer.util.BytecodeUtils;

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
        parent.add(element);
    }

    private void modelType(final DocumentBuilder builder, final Element parent, final ConstantPool constantPool) {

    }
}

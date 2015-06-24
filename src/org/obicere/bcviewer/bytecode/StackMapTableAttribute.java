package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class StackMapTableAttribute extends Attribute {

    private final StackMapFrame[] entries;

    public StackMapTableAttribute(final int attributeNameIndex, final int attributeLength, final StackMapFrame[] entries) {
        super(attributeNameIndex, attributeLength);
        this.entries = entries;
    }

    public StackMapFrame[] getEntries() {
        return entries;
    }
}

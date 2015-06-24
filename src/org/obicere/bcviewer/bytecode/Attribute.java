package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class Attribute {

    private final int attributeNameIndex;

    private final int attributeLength;

    public Attribute(final int attributeNameIndex, final int attributeLength) {
        this.attributeNameIndex = attributeNameIndex;
        this.attributeLength = attributeLength;
    }

    public final int getAttributeNameIndex() {
        return attributeNameIndex;
    }

    public final int getAttributeLength() {
        return attributeLength;
    }

}

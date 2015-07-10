package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class ExceptionsAttribute extends Attribute {

    private final int[] indexTable;

    public ExceptionsAttribute(final int attributeNameIndex, final int attributeLength, final int[] indexTable) {
        super(attributeNameIndex, attributeLength);

        this.indexTable = indexTable;
    }

}

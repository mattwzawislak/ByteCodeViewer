package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class ExceptionsAttribute extends Attribute {

    private final int[] indexTable;

    public ExceptionsAttribute(final int[] indexTable) {

        this.indexTable = indexTable;
    }

    public int[] getIndexTable(){
        return indexTable;
    }

}

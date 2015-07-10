package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class TypeArgumentTarget implements Target {

    private final int offset;

    private final int typeArgumentIndex;

    public TypeArgumentTarget(final int offset, final int typeArgumentIndex) {
        this.offset = offset;
        this.typeArgumentIndex = typeArgumentIndex;
    }

    public int getOffset() {
        return offset;
    }

    public int getTypeArgumentIndex() {
        return typeArgumentIndex;
    }

}

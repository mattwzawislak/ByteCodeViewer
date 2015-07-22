package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class TypeArgumentTarget implements Target {

    private final int targetType;
    private final int offset;

    private final int typeArgumentIndex;

    public TypeArgumentTarget(final int targetType, final int offset, final int typeArgumentIndex) {
        this.targetType = targetType;
        this.offset = offset;
        this.typeArgumentIndex = typeArgumentIndex;
    }

    public int getOffset() {
        return offset;
    }

    public int getTypeArgumentIndex() {
        return typeArgumentIndex;
    }

    @Override
    public int getTargetType() {
        return targetType;
    }
}

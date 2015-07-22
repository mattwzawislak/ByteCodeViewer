package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class TypeParameterBoundTarget implements Target {

    private final int targetType;
    private final int typeParameterIndex;

    private final int boundIndex;

    public TypeParameterBoundTarget(final int targetType, final int typeParameterIndex, final int boundIndex) {
        this.targetType = targetType;
        this.typeParameterIndex = typeParameterIndex;
        this.boundIndex = boundIndex;
    }

    public int getTypeParameterIndex() {
        return typeParameterIndex;
    }

    public int getBoundIndex() {
        return boundIndex;
    }

    @Override
    public int getTargetType() {
        return targetType;
    }

}

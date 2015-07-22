package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class TypeParameterTarget implements Target {

    private final int targetType;
    private final int typeParameterIndex;

    public TypeParameterTarget(final int targetType, final int typeParameterIndex) {
        this.targetType = targetType;
        this.typeParameterIndex = typeParameterIndex;
    }

    public int getTypeParameterIndex() {
        return typeParameterIndex;
    }

    @Override
    public int getTargetType() {
        return targetType;
    }

}

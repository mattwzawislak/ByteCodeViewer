package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class TypeParameterTarget implements Target {

    private final int typeParameterIndex;

    public TypeParameterTarget(final int typeParameterIndex) {
        this.typeParameterIndex = typeParameterIndex;
    }

    public int getTypeParameterIndex() {
        return typeParameterIndex;
    }

}

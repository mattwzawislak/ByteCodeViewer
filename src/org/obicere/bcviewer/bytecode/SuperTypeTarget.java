package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class SuperTypeTarget implements Target {

    private final int targetType;
    private final int superTypeIndex;

    public SuperTypeTarget(final int targetType, final int superTypeIndex) {
        this.targetType = targetType;
        this.superTypeIndex = superTypeIndex;
    }

    public int getSuperTypeIndex() {
        return superTypeIndex;
    }

    @Override
    public int getTargetType() {
        return targetType;
    }

}

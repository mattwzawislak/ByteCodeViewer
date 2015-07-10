package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class SuperTypeTarget implements Target {

    private final int superTypeIndex;

    public SuperTypeTarget(final int superTypeIndex) {
        this.superTypeIndex = superTypeIndex;
    }

    public int getSuperTypeIndex() {
        return superTypeIndex;
    }

}

package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class ThrowsTarget implements Target {

    private final int throwsTypeIndex;

    public ThrowsTarget(final int throwsTypeIndex) {
        this.throwsTypeIndex = throwsTypeIndex;
    }

    public int getThrowsTypeIndex() {
        return throwsTypeIndex;
    }

}

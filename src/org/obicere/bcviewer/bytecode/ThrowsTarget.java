package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class ThrowsTarget implements Target {

    private final int targetType;
    private final int throwsTypeIndex;

    public ThrowsTarget(final int targetType, final int throwsTypeIndex) {
        this.targetType = targetType;
        this.throwsTypeIndex = throwsTypeIndex;
        System.out.println(throwsTypeIndex);
    }

    public int getThrowsTypeIndex() {
        return throwsTypeIndex;
    }

    @Override
    public int getTargetType() {
        return targetType;
    }
}

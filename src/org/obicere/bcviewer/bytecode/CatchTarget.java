package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class CatchTarget implements Target {

    private final int targetType;
    private final int exceptionTableIndex;

    public CatchTarget(final int targetType, final int exceptionTableIndex){
        this.targetType = targetType;
        this.exceptionTableIndex = exceptionTableIndex;
    }

    public int getExceptionTableIndex(){
        return exceptionTableIndex;
    }

    @Override
    public int getTargetType() {
        return targetType;
    }
}

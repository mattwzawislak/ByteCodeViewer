package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class CatchTarget implements Target {

    private final int exceptionTableIndex;

    public CatchTarget(final int exceptionTableIndex){
        this.exceptionTableIndex = exceptionTableIndex;
    }

    public int getExceptionTableIndex(){
        return exceptionTableIndex;
    }

}

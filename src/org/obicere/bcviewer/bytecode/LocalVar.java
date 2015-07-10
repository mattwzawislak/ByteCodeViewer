package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class LocalVar {

    private final int startPC;
    private final int length;
    private final int index;

    public LocalVar(final int startPC, final int length, final int index){
        this.startPC = startPC;
        this.length = length;
        this.index = index;
    }

    public int getStartPC(){
        return startPC;
    }

    public int getLength(){
        return length;
    }

    public int getIndex(){
        return index;
    }

}

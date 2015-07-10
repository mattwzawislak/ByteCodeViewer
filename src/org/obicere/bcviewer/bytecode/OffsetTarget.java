package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class OffsetTarget implements Target {

    private final int offset;

    public OffsetTarget(final int offset){
        this.offset = offset;
    }

    public int getOffset(){
        return offset;
    }

}

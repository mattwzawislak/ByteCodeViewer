package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class ConstantLong extends Constant {

    private static final byte TAG = 5;

    private final long bytes;

    public ConstantLong(final long bytes){
        super(TAG);
        this.bytes = bytes;
    }

    public long getBytes(){
        return bytes;
    }

}

package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class ConstantLong implements Constant {

    private static final byte TAG = 5;

    @Override
    public byte getTag(){
        return TAG;
    }

}

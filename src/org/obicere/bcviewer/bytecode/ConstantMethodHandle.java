package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class ConstantMethodHandle implements Constant {

    private static final byte TAG = 15;

    @Override
    public byte getTag(){
        return TAG;
    }

}

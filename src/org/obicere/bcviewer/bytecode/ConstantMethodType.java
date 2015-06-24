package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class ConstantMethodType implements Constant {

    private static final byte TAG = 16;

    @Override
    public byte getTag(){
        return TAG;
    }

}

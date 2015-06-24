package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class ConstantString implements Constant {

    private static final byte TAG = 8;

    @Override
    public byte getTag(){
        return TAG;
    }

}

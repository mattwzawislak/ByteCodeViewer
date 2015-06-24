package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class ConstantDouble implements Constant {

    private static final byte TAG = 6;

    @Override
    public byte getTag(){
        return TAG;
    }

}

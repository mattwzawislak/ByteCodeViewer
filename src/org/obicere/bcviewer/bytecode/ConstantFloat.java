package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class ConstantFloat implements Constant {

    private static final byte TAG = 4;

    @Override
    public byte getTag(){
        return TAG;
    }

}

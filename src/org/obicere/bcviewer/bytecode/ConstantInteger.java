package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class ConstantInteger implements Constant {

    private static final byte TAG = 3;

    @Override
    public byte getTag(){
        return TAG;
    }

}

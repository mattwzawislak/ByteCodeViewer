package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class ConstantUtf8 implements Constant {

    private static final byte TAG = 1;

    @Override
    public byte getTag(){
        return TAG;
    }

}

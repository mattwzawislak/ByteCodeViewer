package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class ConstantInvokeDynamic implements Constant {

    private static final byte TAG = 18;

    @Override
    public byte getTag(){
        return TAG;
    }

}

package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class ConstantNameAndType implements Constant {

    private static final byte TAG = 12;

    @Override
    public byte getTag(){
        return TAG;
    }

}

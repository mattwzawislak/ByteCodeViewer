package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class ConstantClass implements Constant {

    private static final byte TAG = 7;

    private final int nameIndex;

    public ConstantClass(final int nameIndex){
        this.nameIndex = nameIndex;
    }

    public int getNameIndex(){
        return nameIndex;
    }

    @Override
    public byte getTag(){
        return TAG;
    }

}

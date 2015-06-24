package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class ConstantInterfaceMethodref implements Constant {

    private static final byte TAG = 11;

    private final int classIndex;

    private final int nameAndTypeIndex;

    public ConstantInterfaceMethodref(final int classIndex, final int nameAndTypeIndex) {
        this.classIndex = classIndex;
        this.nameAndTypeIndex = nameAndTypeIndex;
    }

    public int getClassIndex(){
        return classIndex;
    }

    public int getNameAndTypeIndex(){
        return nameAndTypeIndex;
    }

    @Override
    public byte getTag() {
        return TAG;
    }

}

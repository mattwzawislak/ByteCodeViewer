package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class ConstantInterfaceMethodref extends Constant {

    private static final byte TAG = 11;

    private final int classIndex;

    private final int nameAndTypeIndex;

    public ConstantInterfaceMethodref(final int classIndex, final int nameAndTypeIndex) {
        super(TAG);
        this.classIndex = classIndex;
        this.nameAndTypeIndex = nameAndTypeIndex;
    }

    public int getClassIndex(){
        return classIndex;
    }

    public int getNameAndTypeIndex(){
        return nameAndTypeIndex;
    }

}

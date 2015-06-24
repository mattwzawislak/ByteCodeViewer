package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class ConstantFieldref implements Constant {

    private static final byte TAG = 9;

    private final int classIndex;

    private final int nameAndTypeIndex;

    public ConstantFieldref(final int classIndex, final int nameAndTypeIndex) {
        this.classIndex = classIndex;
        this.nameAndTypeIndex = nameAndTypeIndex;
    }

    public int getClassIndex() {
        return classIndex;
    }

    public int getNameAndTypeIndex() {
        return nameAndTypeIndex;
    }

    @Override
    public byte getTag() {
        return TAG;
    }

}

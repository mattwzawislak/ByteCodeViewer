package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class ConstantMethodRef extends Constant {

    private static final byte TAG = 10;

    private final int classIndex;

    private final int nameAndTypeIndex;

    public ConstantMethodRef(final int classIndex, final int nameAndTypeIndex) {
        super(TAG);
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
    public Object get(final ConstantPool constantPool) {
        // Double redirection to get representation of name and type
        return constantPool.get(nameAndTypeIndex).get(constantPool);
    }
}

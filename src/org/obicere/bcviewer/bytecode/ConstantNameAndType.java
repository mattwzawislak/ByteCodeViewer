package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class ConstantNameAndType extends Constant {

    private static final byte TAG = 12;

    private final int nameIndex;

    private final int descriptorIndex;

    public ConstantNameAndType(final int nameIndex, final int descriptorIndex) {
        super(TAG);
        this.nameIndex = nameIndex;
        this.descriptorIndex = descriptorIndex;
    }

    public int getNameIndex() {
        return nameIndex;
    }

    public int getDescriptorIndex() {
        return descriptorIndex;
    }

    @Override
    public Object get(final ConstantPool constantPool) {
        return constantPool.get(descriptorIndex);
    }
}

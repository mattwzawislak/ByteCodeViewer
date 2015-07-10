package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class ConstantMethodType extends Constant {

    private static final byte TAG = 16;

    private final int descriptorIndex;

    public ConstantMethodType(final int descriptorIndex) {
        super(TAG);
        this.descriptorIndex = descriptorIndex;
    }

    public int getDescriptorIndex() {
        return descriptorIndex;
    }

    @Override
    public Object get(final ConstantPool constantPool) {
        return constantPool.get(descriptorIndex);
    }
}

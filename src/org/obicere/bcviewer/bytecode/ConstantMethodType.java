package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.reader.ConstantReader;

/**
 * @author Obicere
 */
public class ConstantMethodType extends Constant {

    private final int descriptorIndex;

    public ConstantMethodType(final int descriptorIndex) {
        super(ConstantReader.CONSTANT_METHOD_TYPE);
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

package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.reader.ConstantReader;

/**
 * @author Obicere
 */
public class ConstantMethodType extends Constant {

    private static final String NAME = "MethodType";

    private final int descriptorIndex;

    public ConstantMethodType(final int descriptorIndex) {
        super(ConstantReader.CONSTANT_METHOD_TYPE);
        this.descriptorIndex = descriptorIndex;
    }

    public int getDescriptorIndex() {
        return descriptorIndex;
    }

    @Override
    public String getName(){
        return NAME;
    }

    @Override
    public String toString(final ConstantPool constantPool) {
        return constantPool.getAsString(descriptorIndex);
    }

    @Override
    public void modelValue(final DocumentBuilder builder) {
        builder.add(builder.getConstantPool().getAsString(descriptorIndex));
    }
}

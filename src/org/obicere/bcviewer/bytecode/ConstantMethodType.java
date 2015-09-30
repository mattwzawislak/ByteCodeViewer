package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;
import org.obicere.bcviewer.reader.ConstantReader;

import javax.swing.text.Element;

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
    public void modelValue(final BytecodeDocumentBuilder builder, final Element parent) {
        builder.addPlain(builder.getConstantPool().getAsString(descriptorIndex));
    }
}

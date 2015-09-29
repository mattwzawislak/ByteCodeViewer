package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;
import org.obicere.bcviewer.reader.ConstantReader;

import javax.swing.text.Element;

/**
 * @author Obicere
 */
public class ConstantNameAndType extends Constant {

    private static final String NAME = "NameAndType";

    private final int nameIndex;

    private final int descriptorIndex;

    public ConstantNameAndType(final int nameIndex, final int descriptorIndex) {
        super(ConstantReader.CONSTANT_NAME_AND_TYPE);
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
    public String getName() {
        return NAME;
    }

    @Override
    public String toString(final ConstantPool constantPool) {
        return constantPool.getAsString(nameIndex) + ";" + constantPool.getAsString(descriptorIndex);
    }

    @Override
    public void modelValue(final BytecodeDocumentBuilder builder, final Element parent) {
        final ConstantPool constantPool = builder.getConstantPool();

        builder.addPlain(parent, constantPool.getAsString(nameIndex));
        builder.tab(parent);
        builder.addPlain(parent, constantPool.getAsString(descriptorIndex));
    }
}

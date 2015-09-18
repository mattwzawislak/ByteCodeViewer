package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.Element;
import org.obicere.bcviewer.dom.bytecode.ConstantElement;
import org.obicere.bcviewer.dom.literals.ParameterUtf8Element;
import org.obicere.bcviewer.reader.ConstantReader;

/**
 * @author Obicere
 */
public class ConstantNameAndType extends Constant {

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
    public String toString(final ConstantPool constantPool) {
        return constantPool.getAsString(nameIndex) + ";" + constantPool.getAsString(descriptorIndex);
    }


    @Override
    public void model(final DocumentBuilder builder, final Element parent) {
        final ConstantPool constantPool = builder.getConstantPool();

        parent.add(new ConstantElement(this, builder));
        parent.add(new ParameterUtf8Element("name", constantPool.getAsString(nameIndex), builder));
        parent.add(new ParameterUtf8Element("descriptor", constantPool.getAsString(descriptorIndex), builder));
    }
}

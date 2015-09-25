package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.Element;
import org.obicere.bcviewer.dom.bytecode.ConstantElement;
import org.obicere.bcviewer.dom.literals.ParameterPlainElement;
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
    public String toString(final ConstantPool constantPool) {
        return constantPool.getAsString(descriptorIndex);
    }

    @Override
    public void model(final DocumentBuilder builder, final Element parent) {
        final ConstantPool constantPool = builder.getConstantPool();

        parent.add(new ConstantElement(this, builder));
        parent.add(new ParameterPlainElement("descriptor", constantPool.getAsString(descriptorIndex), builder));
    }
}

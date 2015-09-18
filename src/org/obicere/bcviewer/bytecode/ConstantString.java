package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.Element;
import org.obicere.bcviewer.dom.bytecode.ConstantElement;
import org.obicere.bcviewer.dom.literals.ParameterStringElement;
import org.obicere.bcviewer.reader.ConstantReader;

/**
 * @author Obicere
 */
public class ConstantString extends Constant {

    private final int stringIndex;

    public ConstantString(final int stringIndex) {
        super(ConstantReader.CONSTANT_STRING);
        this.stringIndex = stringIndex;
    }

    public int getStringIndex() {
        return stringIndex;
    }

    @Override
    public String toString(final ConstantPool constantPool) {
        return constantPool.getAsString(stringIndex);
    }

    @Override
    public void model(final DocumentBuilder builder, final Element parent) {
        parent.add(new ConstantElement(this, builder));
        parent.add(new ParameterStringElement("string", builder.getConstantPool().getAsString(stringIndex), builder));
    }
}

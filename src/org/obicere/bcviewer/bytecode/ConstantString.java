package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;
import org.obicere.bcviewer.reader.ConstantReader;

import javax.swing.text.Element;

/**
 * @author Obicere
 */
public class ConstantString extends Constant {

    private static final String NAME = "String";

    private final int stringIndex;

    public ConstantString(final int stringIndex) {
        super(ConstantReader.CONSTANT_STRING);
        this.stringIndex = stringIndex;
    }

    public int getStringIndex() {
        return stringIndex;
    }

    @Override
    public String getName(){
        return NAME;
    }

    @Override
    public String toString(final ConstantPool constantPool) {
        return constantPool.getAsString(stringIndex);
    }

    @Override
    public void modelValue(final BytecodeDocumentBuilder builder) {
        builder.addString(builder.getConstantPool().getAsString(stringIndex));
    }
}

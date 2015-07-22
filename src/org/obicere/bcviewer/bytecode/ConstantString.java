package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.reader.ConstantReader;

/**
 * @author Obicere
 */
public class ConstantString extends Constant {

    private final int stringIndex;

    public ConstantString(final int stringIndex){
        super(ConstantReader.CONSTANT_STRING);
        this.stringIndex = stringIndex;
    }

    public int getStringIndex(){
        return stringIndex;
    }

    @Override
    public String toString(final ConstantPool constantPool) {
        return constantPool.getAsString(stringIndex);
    }
}

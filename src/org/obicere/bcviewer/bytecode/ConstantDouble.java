package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.reader.ConstantReader;

/**
 * @author Obicere
 */
public class ConstantDouble extends Constant {

    private final double bytes;

    public ConstantDouble(final double bytes){
        super(ConstantReader.CONSTANT_DOUBLE);
        this.bytes = bytes;
    }

    public double getBytes(){
        return bytes;
    }

    @Override
    public Object get(final ConstantPool constantPool) {
        return bytes;
    }
}

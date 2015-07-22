package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.reader.ConstantReader;

/**
 * @author Obicere
 */
public class ConstantLong extends Constant {

    private final long bytes;

    public ConstantLong(final long bytes){
        super(ConstantReader.CONSTANT_LONG);
        this.bytes = bytes;
    }

    public long getBytes(){
        return bytes;
    }

    @Override
    public String toString(final ConstantPool constantPool) {
        return String.valueOf(bytes);
    }
}

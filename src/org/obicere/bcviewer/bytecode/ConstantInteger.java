package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.reader.ConstantReader;

/**
 * @author Obicere
 */
public class ConstantInteger extends Constant {

    private final int bytes;

    public ConstantInteger(final int bytes) {
        super(ConstantReader.CONSTANT_INTEGER);
        this.bytes = bytes;
    }

    public int getBytes() {
        return bytes;
    }

    @Override
    public String toString(final ConstantPool constantPool) {
        return String.valueOf(bytes);
    }
}

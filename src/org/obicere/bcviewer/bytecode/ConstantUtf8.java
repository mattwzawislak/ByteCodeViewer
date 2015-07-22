package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.reader.ConstantReader;

/**
 * @author Obicere
 */
public class ConstantUtf8 extends Constant {

    private final String bytes;

    public ConstantUtf8(final String bytes) {
        super(ConstantReader.CONSTANT_UTF8);
        this.bytes = bytes;
    }

    public int getLength() {
        return bytes.length();
    }

    public String getBytes() {
        return bytes;
    }

    @Override
    public Object get(final ConstantPool constantPool) {
        return bytes;
    }
}

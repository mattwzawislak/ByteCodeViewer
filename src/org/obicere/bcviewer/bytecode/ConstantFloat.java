package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.reader.ConstantReader;

/**
 * @author Obicere
 */
public class ConstantFloat extends Constant {

    private final float bytes;

    public ConstantFloat(final float bytes) {
        super(ConstantReader.CONSTANT_FLOAT);
        this.bytes = bytes;
    }

    public float getBytes() {
        return bytes;
    }

    @Override
    public Object get(final ConstantPool constantPool) {
        return bytes;
    }
}

package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.reader.ConstantReader;

/**
 * @author Obicere
 */
public class ConstantFloat extends Constant {

    private static final String NAME = "Float";

    private final float bytes;

    public ConstantFloat(final float bytes) {
        super(ConstantReader.CONSTANT_FLOAT);
        this.bytes = bytes;
    }

    public float getBytes() {
        return bytes;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String toString(final ConstantPool constantPool) {
        return String.valueOf(bytes);
    }

    @Override
    public void modelValue(final DocumentBuilder builder) {
        builder.add(bytes);
    }
}

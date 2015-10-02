package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.reader.ConstantReader;

/**
 * @author Obicere
 */
public class ConstantInteger extends Constant {

    private static final String NAME = "Integer";

    private final int bytes;

    public ConstantInteger(final int bytes) {
        super(ConstantReader.CONSTANT_INTEGER);
        this.bytes = bytes;
    }

    public int getBytes() {
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

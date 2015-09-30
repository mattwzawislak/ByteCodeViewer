package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;
import org.obicere.bcviewer.reader.ConstantReader;

import javax.swing.text.Element;

/**
 * @author Obicere
 */
public class ConstantLong extends Constant {

    private static final String NAME = "Long";

    private final long bytes;

    public ConstantLong(final long bytes) {
        super(ConstantReader.CONSTANT_LONG);
        this.bytes = bytes;
    }

    public long getBytes() {
        return bytes;
    }

    @Override
    public String getName(){
        return NAME;
    }

    @Override
    public String toString(final ConstantPool constantPool) {
        return String.valueOf(bytes);
    }

    @Override
    public void modelValue(final BytecodeDocumentBuilder builder, final Element parent) {
        builder.add(bytes);
    }
}

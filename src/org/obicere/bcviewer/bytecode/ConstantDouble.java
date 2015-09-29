package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;
import org.obicere.bcviewer.reader.ConstantReader;

import javax.swing.text.Element;

/**
 * @author Obicere
 */
public class ConstantDouble extends Constant {

    private static final String NAME = "Double";

    private final double bytes;

    public ConstantDouble(final double bytes) {
        super(ConstantReader.CONSTANT_DOUBLE);
        this.bytes = bytes;
    }

    public double getBytes() {
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
    public void modelValue(final BytecodeDocumentBuilder builder, final Element parent) {
        builder.add(parent, bytes);
    }
}

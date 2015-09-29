package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;
import org.obicere.bcviewer.reader.ConstantReader;

import javax.swing.text.Element;

/**
 * @author Obicere
 */
public class ConstantUtf8 extends Constant {

    private static final String NAME = "Utf8";

    private final String bytes;

    public ConstantUtf8(final String bytes) {
        super(ConstantReader.CONSTANT_UTF8);
        this.bytes = bytes;
    }

    public int length() {
        return bytes.length();
    }

    public String getBytes() {
        return bytes;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String toString(final ConstantPool constantPool) {
        return bytes;
    }

    @Override
    public void modelValue(final BytecodeDocumentBuilder builder, final Element parent) {
        builder.addString(parent, bytes);
    }
}

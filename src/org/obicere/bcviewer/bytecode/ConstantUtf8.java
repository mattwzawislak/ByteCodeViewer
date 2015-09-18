package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.Element;
import org.obicere.bcviewer.dom.bytecode.ConstantElement;
import org.obicere.bcviewer.dom.literals.ParameterStringElement;
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

    public int length() {
        return bytes.length();
    }

    public String getBytes() {
        return bytes;
    }

    @Override
    public String toString(final ConstantPool constantPool) {
        return bytes;
    }

    @Override
    public void model(final DocumentBuilder builder, final Element parent) {
        parent.add(new ConstantElement(this, builder));
        parent.add(new ParameterStringElement("bytes", bytes, builder));
    }
}

package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.reader.ConstantReader;

/**
 * @author Obicere
 */
public class ConstantClass extends Constant {

    private static final String NAME = "Class";

    private final int nameIndex;

    public ConstantClass(final int nameIndex) {
        super(ConstantReader.CONSTANT_CLASS);
        this.nameIndex = nameIndex;
    }

    @Override
    public String getName() {
        return NAME;
    }

    public int getNameIndex() {
        return nameIndex;
    }

    @Override
    public String toString(final ConstantPool constantPool) {
        // nameIndex points to a ConstantUtf8
        return constantPool.getAsString(nameIndex);
    }

    @Override
    public void modelValue(final DocumentBuilder builder) {
        builder.add(builder.getConstantPool().getAsString(getNameIndex()));
    }
}

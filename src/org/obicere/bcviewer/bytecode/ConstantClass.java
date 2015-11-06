package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.reader.ConstantReader;
import org.obicere.bcviewer.util.BytecodeUtils;

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
        builder.indent();
        builder.newLine();
        final String name = builder.getConstantPool().getAsString(getNameIndex());
        final boolean importMode = builder.getDomain().getSettingsController().getSettings().getBoolean("code.importMode");
        if (importMode) {
            builder.add(BytecodeUtils.getClassName(name));
        } else {
            builder.add(BytecodeUtils.getQualifiedName(name));
        }
        builder.add(".");
        builder.addKeyword("class");
        builder.unindent();
    }
}

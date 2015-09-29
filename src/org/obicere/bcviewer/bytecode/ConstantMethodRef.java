package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;
import org.obicere.bcviewer.reader.ConstantReader;

import javax.swing.text.Element;

/**
 * @author Obicere
 */
public class ConstantMethodRef extends Constant {

    private static final String NAME = "MethodRef";

    private final int classIndex;

    private final int nameAndTypeIndex;

    public ConstantMethodRef(final int classIndex, final int nameAndTypeIndex) {
        super(ConstantReader.CONSTANT_METHOD_REF);
        this.classIndex = classIndex;
        this.nameAndTypeIndex = nameAndTypeIndex;
    }

    public int getClassIndex() {
        return classIndex;
    }

    public int getNameAndTypeIndex() {
        return nameAndTypeIndex;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String toString(final ConstantPool constantPool) {
        // Double redirection to toString representation of name and type
        return constantPool.getAsString(classIndex) + ";" + constantPool.getAsString(nameAndTypeIndex);
    }

    @Override
    public void modelValue(final BytecodeDocumentBuilder builder, final Element parent) {
        final ConstantPool constantPool = builder.getConstantPool();
        builder.addPlain(parent, constantPool.getAsString(classIndex));
        builder.tab(parent);
        builder.addPlain(parent, constantPool.getAsString(nameAndTypeIndex));
    }
}

package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.reader.ConstantReader;

/**
 * @author Obicere
 */
public class ConstantMethodRef extends Constant {

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
    public String toString(final ConstantPool constantPool) {
        // Double redirection to toString representation of name and type
        return constantPool.getAsString(nameAndTypeIndex) + ";" + constantPool.getAsString(nameAndTypeIndex);
    }
}

package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.reader.ConstantReader;

/**
 * @author Obicere
 */
public class ConstantInterfaceMethodRef extends Constant {

    private final int classIndex;

    private final int nameAndTypeIndex;

    public ConstantInterfaceMethodRef(final int classIndex, final int nameAndTypeIndex) {
        super(ConstantReader.CONSTANT_INTERFACE_METHOD_REF);
        this.classIndex = classIndex;
        this.nameAndTypeIndex = nameAndTypeIndex;
    }

    public int getClassIndex(){
        return classIndex;
    }

    public int getNameAndTypeIndex(){
        return nameAndTypeIndex;
    }

    @Override
    public String toString(final ConstantPool constantPool) {
        // Double redirection to toString representation of name and type
        return constantPool.getAsString(classIndex) + ";" + constantPool.getAsString(nameAndTypeIndex);
    }
}

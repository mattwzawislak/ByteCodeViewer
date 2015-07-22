package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.reader.ConstantReader;

/**
 * @author Obicere
 */
public class ConstantClass extends Constant {

    private final int nameIndex;

    public ConstantClass(final int nameIndex){
        super(ConstantReader.CONSTANT_CLASS);
        this.nameIndex = nameIndex;
    }

    public int getNameIndex(){
        return nameIndex;
    }

    @Override
    public String toString(final ConstantPool constantPool) {
        // nameIndex points to a ConstantUtf8
        return constantPool.getAsString(nameIndex);
    }
}

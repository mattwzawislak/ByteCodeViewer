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
    public Object get(final ConstantPool constantPool) {
        return constantPool.get(nameIndex);
    }
}

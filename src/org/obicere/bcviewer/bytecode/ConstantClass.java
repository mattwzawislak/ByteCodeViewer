package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class ConstantClass extends Constant {

    private static final byte TAG = 7;

    private final int nameIndex;

    public ConstantClass(final int nameIndex){
        super(TAG);
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

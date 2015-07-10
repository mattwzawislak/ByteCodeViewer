package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class ConstantString extends Constant {

    private static final byte TAG = 8;

    private final int stringIndex;

    public ConstantString(final int stringIndex){
        super(TAG);
        this.stringIndex = stringIndex;
    }

    public int getStringIndex(){
        return stringIndex;
    }

    @Override
    public Object get(final ConstantPool constantPool) {
        return constantPool.get(stringIndex);
    }
}

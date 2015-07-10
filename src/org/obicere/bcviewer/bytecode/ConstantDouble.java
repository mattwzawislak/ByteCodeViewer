package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class ConstantDouble extends Constant {

    private static final byte TAG = 6;

    private final double bytes;

    public ConstantDouble(final double bytes){
        super(TAG);
        this.bytes = bytes;
    }

    public double getBytes(){
        return bytes;
    }

    @Override
    public Object get(final ConstantPool constantPool) {
        return bytes;
    }
}

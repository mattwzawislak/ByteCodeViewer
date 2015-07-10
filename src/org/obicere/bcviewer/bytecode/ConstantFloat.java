package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class ConstantFloat extends Constant {

    private static final byte TAG = 4;

    private final float bytes;

    public ConstantFloat(final float bytes) {
        super(TAG);
        this.bytes = bytes;
    }

    public float getBytes() {
        return bytes;
    }

    @Override
    public Object get(final ConstantPool constantPool) {
        return bytes;
    }
}

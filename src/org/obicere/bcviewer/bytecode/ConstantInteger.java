package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class ConstantInteger extends Constant {

    private static final byte TAG = 3;

    private final int bytes;

    public ConstantInteger(final int bytes) {
        super(TAG);
        this.bytes = bytes;
    }

    public int getBytes() {
        return bytes;
    }

    @Override
    public Object get(final ConstantPool constantPool) {
        return bytes;
    }
}

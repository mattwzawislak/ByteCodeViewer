package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class ConstantUtf8 extends Constant {

    private static final byte TAG = 1;

    private final String bytes;

    public ConstantUtf8(final String bytes) {
        super(TAG);
        this.bytes = bytes;
    }

    public int getLength() {
        return bytes.length();
    }

    public String getBytes() {
        return bytes;
    }

}

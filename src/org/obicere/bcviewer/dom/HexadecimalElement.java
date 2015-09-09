package org.obicere.bcviewer.dom;

/**
 */
public class HexadecimalElement extends IntegerElement {

    private static final int RADIX = 16;

    public HexadecimalElement(final String name) {
        this(name, 0);
    }

    public HexadecimalElement(final String name, final int number) {
        super(name, number, RADIX);
    }

    @Override
    protected String valueOf(final int number) {
        return "0x" + Integer.toHexString(number);
    }
}

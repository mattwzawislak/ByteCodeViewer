package org.obicere.bcviewer.dom;

/**
 */
public class BinaryElement extends IntegerElement {

    private static final int RADIX = 2;

    public BinaryElement(final String name) {
        this(name, 0);
    }

    public BinaryElement(final String name, final int number) {
        super(name, number, RADIX);
    }

    @Override
    protected String valueOf(final int number) {
        return "0b" + Integer.toBinaryString(number);
    }

}

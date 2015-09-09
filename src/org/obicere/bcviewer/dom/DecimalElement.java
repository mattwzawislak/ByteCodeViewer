package org.obicere.bcviewer.dom;

/**
 */
public class DecimalElement extends IntegerElement {

    private static final int RADIX = 10;

    public DecimalElement(final String name) {
        this(name, 0);
    }

    public DecimalElement(final String name, final int number) {
        super(name, number, RADIX);
    }
}

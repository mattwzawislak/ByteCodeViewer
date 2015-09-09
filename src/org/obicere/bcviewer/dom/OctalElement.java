package org.obicere.bcviewer.dom;

/**
 */
public class OctalElement extends IntegerElement {

    private static final int RADIX = 8;

    public OctalElement(final String name) {
        this(name, 0);
    }

    public OctalElement(final String name, final int number) {
        super(name, number, RADIX);
    }

    @Override
    protected String valueOf(final int number) {
        return "0" + Integer.toOctalString(number);
    }
}

package org.obicere.bcviewer.dom;

/**
 */
public class IntegerElement extends TextElement {

    private int number;

    private final int radix;

    public IntegerElement(final String name) {
        this(name, 0, 10);
    }

    public IntegerElement(final String name, final int number, final int radix) {
        super(name);
        this.number = number;
        this.radix = radix;
    }

    public void setNumber(final int number) {
        this.number = number;
        setText(valueOf(number));
    }

    public int getNumber() {
        return number;
    }

    protected String valueOf(final int number) {
        return Integer.toString(number, radix);
    }
}

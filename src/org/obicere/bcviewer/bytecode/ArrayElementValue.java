package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class ArrayElementValue extends ElementValue {

    private static final int TAG = '[';

    private final ElementValue[] values;

    public ArrayElementValue(final ElementValue[] values) {
        super(TAG);
        this.values = values;
    }

    public ElementValue[] getValues() {
        return values;
    }
}

package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class IntegerElementValue extends ElementValue {

    private static final int TAG = 'I';

    private final int constantValueIndex;

    public IntegerElementValue(final int constantValueIndex) {
        super(TAG);
        this.constantValueIndex = constantValueIndex;
    }

    public int getConstantValueIndex() {
        return constantValueIndex;
    }
}

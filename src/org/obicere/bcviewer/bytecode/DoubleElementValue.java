package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class DoubleElementValue extends ElementValue {

    private static final int TAG = 'D';

    private final int constantValueIndex;

    public DoubleElementValue(final int constantValueIndex) {
        super(TAG);
        this.constantValueIndex = constantValueIndex;
    }

    public int getConstantValueIndex() {
        return constantValueIndex;
    }
}

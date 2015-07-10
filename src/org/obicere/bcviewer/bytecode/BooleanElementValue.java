package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class BooleanElementValue extends ElementValue {

    private static final int TAG = 'Z';

    private final int constantValueIndex;

    public BooleanElementValue(final int constantValueIndex) {
        super(TAG);
        this.constantValueIndex = constantValueIndex;
    }

    public int getConstantValueIndex() {
        return constantValueIndex;
    }
}

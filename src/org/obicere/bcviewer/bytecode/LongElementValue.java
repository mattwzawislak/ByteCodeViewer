package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class LongElementValue extends ElementValue {

    private static final int TAG = 'J';

    private final int constantValueIndex;

    public LongElementValue(final int constantValueIndex) {
        super(TAG);
        this.constantValueIndex = constantValueIndex;
    }

    public int getConstantValueIndex() {
        return constantValueIndex;
    }
}

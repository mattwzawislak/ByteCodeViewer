package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class ShortElementValue extends ElementValue {

    private static final int TAG = 'S';

    private final int constantValueIndex;

    public ShortElementValue(final int constantValueIndex) {
        super(TAG);
        this.constantValueIndex = constantValueIndex;
    }

    public int getConstantValueIndex() {
        return constantValueIndex;
    }
}

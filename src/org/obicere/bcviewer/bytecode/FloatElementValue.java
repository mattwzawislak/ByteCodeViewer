package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class FloatElementValue extends ElementValue {

    private static final int TAG = 'F';

    private final int constantValueIndex;

    public FloatElementValue(final int constantValueIndex) {
        super(TAG);
        this.constantValueIndex = constantValueIndex;
    }

    public int getConstantValueIndex() {
        return constantValueIndex;
    }
}

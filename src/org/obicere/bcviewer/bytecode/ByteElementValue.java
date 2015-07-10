package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class ByteElementValue extends ElementValue {

    private static final int TAG = 'B';

    private final int constantValueIndex;

    public ByteElementValue(final int constantValueIndex) {
        super(TAG);
        this.constantValueIndex = constantValueIndex;
    }

    public int getConstantValueIndex() {
        return constantValueIndex;
    }
}

package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class StringElementValue extends ElementValue {

    private static final int TAG = 's';

    private final int constantValueIndex;

    public StringElementValue(final int constantValueIndex) {
        super(TAG);
        this.constantValueIndex = constantValueIndex;
    }

    public int getConstantValueIndex() {
        return constantValueIndex;
    }
}

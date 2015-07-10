package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class CharacterElementValue extends ElementValue {

    private static final int TAG = 'C';

    private final int constantValueIndex;

    public CharacterElementValue(final int constantValueIndex) {
        super(TAG);
        this.constantValueIndex = constantValueIndex;
    }

    public int getConstantValueIndex() {
        return constantValueIndex;
    }
}
package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class ConstantValueAttribute extends Attribute {

    private final int constantValueIndex;

    public ConstantValueAttribute(final int constantValueIndex) {
        this.constantValueIndex = constantValueIndex;
    }

    public int getConstantValueIndex() {
        return constantValueIndex;
    }

}

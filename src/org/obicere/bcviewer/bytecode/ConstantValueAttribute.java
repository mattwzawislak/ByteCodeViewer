package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class ConstantValueAttribute extends Attribute {

    private static final int ATTRIBUTE_LENGTH = 2;

    private final int constantValueIndex;

    public ConstantValueAttribute(final int attributeNameIndex, final int constantValueIndex) {
        super(attributeNameIndex, ATTRIBUTE_LENGTH);
        this.constantValueIndex = constantValueIndex;
    }

    public int getConstantValueIndex() {
        return constantValueIndex;
    }

}

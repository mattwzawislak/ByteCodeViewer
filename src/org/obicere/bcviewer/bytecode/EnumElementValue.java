package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class EnumElementValue extends ElementValue {

    private static final int TAG = 'e';

    private final int typeNameIndex;
    private final int constNameIndex;

    public EnumElementValue(final int typeNameIndex, final int constNameIndex) {
        super(TAG);
        this.typeNameIndex = typeNameIndex;
        this.constNameIndex = constNameIndex;
    }

    public int getTypeNameIndex() {
        return typeNameIndex;
    }

    public int getConstNameIndex() {
        return constNameIndex;
    }
}

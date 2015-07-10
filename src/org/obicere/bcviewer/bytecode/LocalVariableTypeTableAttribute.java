package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class LocalVariableTypeTableAttribute extends Attribute {

    private final LocalVariableType[] localVariableTypeTable;

    public LocalVariableTypeTableAttribute(final int attributeNameIndex, final int attributeLength, final LocalVariableType[] localVariableTypeTable) {
        super(attributeNameIndex, attributeLength);

        if (localVariableTypeTable == null) {
            throw new NullPointerException("local variable type table not defined.");
        }

        this.localVariableTypeTable = localVariableTypeTable;
    }

    public LocalVariableType[] getLocalVariableTypeTable() {
        return localVariableTypeTable;
    }

}

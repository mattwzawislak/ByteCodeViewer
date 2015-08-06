package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class LocalVariableTableAttribute extends Attribute {

    private final LocalVariable[] localVariableTable;

    public LocalVariableTableAttribute(final LocalVariable[] localVariableTable) {

        if (localVariableTable == null) {
            throw new NullPointerException("local variable table not defined.");
        }

        this.localVariableTable = localVariableTable;
    }

    public LocalVariable[] getLocalVariableTable() {
        return localVariableTable;
    }

}

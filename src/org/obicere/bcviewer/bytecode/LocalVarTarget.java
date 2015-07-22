package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class LocalVarTarget implements Target {

    private final int targetType;
    private final LocalVar[] table;

    public LocalVarTarget(final int targetType, final LocalVar[] table) {
        this.targetType = targetType;
        this.table = table;
    }

    public LocalVar[] getTable() {
        return table;
    }

    @Override
    public int getTargetType() {
        return targetType;
    }
}

package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class LocalVarTarget implements Target {

    private final LocalVar[] table;

    public LocalVarTarget(final LocalVar[] table) {
        this.table = table;
    }

    public LocalVar[] getTable() {
        return table;
    }

}

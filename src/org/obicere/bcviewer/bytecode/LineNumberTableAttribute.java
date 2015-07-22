package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class LineNumberTableAttribute implements Attribute {

    private final LineNumber[] lineNumberTable;

    public LineNumberTableAttribute(final LineNumber[] lineNumberTable) {

        if (lineNumberTable == null) {
            throw new NullPointerException("line number table not defined.");
        }

        this.lineNumberTable = lineNumberTable;
    }

    public LineNumber[] getLineNumberTable() {
        return lineNumberTable;
    }
}

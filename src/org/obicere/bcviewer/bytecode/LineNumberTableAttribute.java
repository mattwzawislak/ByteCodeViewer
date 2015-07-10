package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class LineNumberTableAttribute extends Attribute {

    private final LineNumber[] lineNumberTable;

    public LineNumberTableAttribute(final int attributeNameIndex, final int attributeLength, final LineNumber[] lineNumberTable) {
        super(attributeNameIndex, attributeLength);

        if (lineNumberTable == null) {
            throw new NullPointerException("line number table not defined.");
        }

        this.lineNumberTable = lineNumberTable;
    }

    public LineNumber[] getLineNumberTable() {
        return lineNumberTable;
    }
}

package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class LineNumber {

    private final int startPC;

    private final int lineNumber;

    public LineNumber(final int startPC, final int lineNumber) {
        this.startPC = startPC;
        this.lineNumber = lineNumber;
    }

    public int getStartPC() {
        return startPC;
    }

    public int getLineNumber() {
        return lineNumber;
    }

}

package org.obicere.bcviewer.dom.awt;

/**
 */
public class QueryResult {

    private final int line;

    private final int start;

    private final int end;

    public QueryResult(final int line, final int start, final int end) {
        this.line = line;
        this.start = start;
        this.end = end;
    }

    public int getLine() {
        return line;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }
}

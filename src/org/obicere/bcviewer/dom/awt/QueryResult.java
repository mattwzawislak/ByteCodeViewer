package org.obicere.bcviewer.dom.awt;

/**
 */
public class QueryResult {

    private final int startLine;

    private final int endLine;

    private final int start;

    private final int end;

    public QueryResult(final int startLine, final int endLine, final int start, final int end) {
        this.startLine = startLine;
        this.endLine = endLine;
        this.start = start;
        this.end = end;
    }

    public int getStartLine() {
        return startLine;
    }

    public int getEndLine() {
        return endLine;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }
}

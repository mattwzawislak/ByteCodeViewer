package org.obicere.bcviewer.dom.awt;

import org.obicere.bcviewer.dom.Document;
import org.obicere.bcviewer.dom.Line;

import java.util.List;

/**
 */
public class NextQuerySearcher implements QuerySearcher {

    @Override
    public QueryResult search(final Document document, final Query query) {
        final QueryResult current = query.current();
        final String search = query.getInput();

        final int start = (current != null ? current.getStartLine() : 0);
        final int minimumIndex = (current != null ? current.getStart() : -1);
        final int maxCount = document.getLineCount();

        final boolean ignoreCase = query.isIgnoreCase();

        // first check the line of the previous result for more results
        final Line thisLine = document.getLine(start);
        final int searchIndex = findInLine(thisLine, search, ignoreCase, minimumIndex + 1);
        if (searchIndex >= 0) {
            return new QueryResult(start, start, searchIndex, searchIndex + search.length());
        }

        // scan the remaining lines past the starting line
        final List<Line> lines = document.getLines(start + 1, maxCount);
        final QueryResult result = scan(lines, search, ignoreCase, start + 1);
        if (result != null) {
            return result;
        }

        // loop back and start from the top of the document, this will
        // contain the previous result if it is indeed still valid
        final List<Line> loopBackLines = document.getLines(0, start);
        return scan(loopBackLines, search, ignoreCase, 0);
    }

    private QueryResult scan(final List<Line> lines, final String search, final boolean ignoreCase, final int lineStart) {
        for (int i = 0; i < lines.size(); i++) {
            final Line line = lines.get(i);
            final int index = findInLine(line, search, ignoreCase, 0);
            if (index >= 0) {
                final int lineIndex = lineStart + i;
                return new QueryResult(lineIndex, lineIndex, index, index + search.length());
            }
        }
        return null;
    }

    private int findInLine(final Line line, final String search, final boolean ignoreCase, final int start) {
        String nextText = line.getText();
        if (ignoreCase) {
            nextText = nextText.toLowerCase();
        }

        return nextText.indexOf(search, start);
    }
}

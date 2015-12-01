package org.obicere.bytecode.viewer.dom.awt;

import org.obicere.bytecode.viewer.dom.Document;
import org.obicere.bytecode.viewer.dom.Line;

import java.util.List;

/**
 */
public class PreviousQuerySearcher implements QuerySearcher {

    @Override
    public QueryResult search(final Document document, final Query query) {
        final QueryResult current = query.current();
        final String search = query.getInput();

        final int start = (current != null ? current.getStartLine() : 0);
        final int minimumIndex = (current != null ? current.getStart() : 0);
        final int maxCount = document.getLineCount();

        final boolean ignoreCase = query.isIgnoreCase();

        // first check the line of the previous result for more results
        final Line thisLine = document.getLine(start);
        final int searchIndex = findInLine(thisLine, search, ignoreCase, minimumIndex - 1);
        if (searchIndex >= 0) {
            return new QueryResult(start, start, searchIndex, searchIndex + search.length());
        }

        // scan the remaining lines before the starting line
        final List<Line> lines = document.getLines(0, start);
        final QueryResult result = scan(lines, search, ignoreCase, 0);
        if (result != null) {
            return result;
        }

        // loop back and start from the bottom of the document
        final List<Line> loopBackLines = document.getLines(start, maxCount);
        return scan(loopBackLines, search, ignoreCase, start);
    }

    private QueryResult scan(final List<Line> lines, final String search, final boolean ignoreCase, final int startLine) {
        for (int i = lines.size() - 1; i >= 0; i--) {
            final Line line = lines.get(i);
            final int index = findInLine(line, search, ignoreCase, -1);
            if (index >= 0) {
                final int lineIndex = i + startLine;
                return new QueryResult(lineIndex, lineIndex, index, index + search.length());
            }
        }
        return null;
    }

    private int findInLine(final Line line, final String search, final boolean ignoreCase, final int end) {
        String nextText = line.getText();
        if (ignoreCase) {
            nextText = nextText.toLowerCase();
        }

        if (end == -1) {
            return nextText.lastIndexOf(search);
        }
        return nextText.lastIndexOf(search, end);
    }
}

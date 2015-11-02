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
        final int minimumIndex = (current != null ? current.getStart() : 0);
        final int maxCount = document.getLineCount();

        final boolean ignoreCase = query.isIgnoreCase();

        final List<Line> lines = document.getLines(start, maxCount);

        if (lines.size() > 0) {
            final Line thisLine = lines.get(0);
            String thisLineText = thisLine.getText();

            if (ignoreCase) {
                thisLineText = thisLineText.toLowerCase();
            }

            final int searchIndex = thisLineText.indexOf(search, minimumIndex + 1);
            if (searchIndex >= 0) {
                return new QueryResult(start, start, searchIndex, searchIndex + search.length());
            }

            for (int i = 1; i < lines.size(); i++) {
                final Line nextLine = lines.get(i);
                String nextText = nextLine.getText();
                if (ignoreCase) {
                    nextText = nextText.toLowerCase();
                }

                final int nextIndex = nextText.indexOf(search);
                if (nextIndex >= 0) {
                    return new QueryResult(start + i, start + i, nextIndex, nextIndex + search.length());
                }
            }
        }

        final List<Line> loopBackLines = document.getLines(0, start);

        for (int i = 0; i < loopBackLines.size(); i++) {
            final Line nextLine = loopBackLines.get(i);
            String nextText = nextLine.getText();
            if (ignoreCase) {
                nextText = nextText.toLowerCase();
            }

            final int nextIndex = nextText.indexOf(search);
            if (nextIndex >= 0) {
                return new QueryResult(i, i, nextIndex, nextIndex + search.length());
            }
        }

        return null;
    }
}

package org.obicere.bcviewer.dom.awt;

import org.obicere.bcviewer.dom.Document;
import org.obicere.bcviewer.dom.Line;

import java.util.List;

/**
 * @author Obicere
 */
public class PreviousMultiLineQuerySearcher implements QuerySearcher {

    @Override
    public QueryResult search(final Document document, final Query query) {
        final String input;
        if (query.isIgnoreCase()) {
            input = query.getInput().toLowerCase();
        } else {
            input = query.getInput();
        }
        final String[] lines = input.split("\n");
        final int inputLength = lines.length;

        if (inputLength == 0) {
            return null;
        }

        final QueryResult current = query.current();

        final int start = (current == null) ? document.getLineCount() : current.getStartLine() + inputLength;

        final List<Line> section = document.getLines(0, start);

        final QueryResult searchFirst = search(section, lines, 0, query.isIgnoreCase());
        if (searchFirst != null) {
            return searchFirst;
        }

        final List<Line> wrapSection = document.getLines(start, document.getLineCount());
        return search(wrapSection, lines, start, query.isIgnoreCase());
    }

    private QueryResult search(final List<Line> section, final String[] inputs, final int startOffset, final boolean ignoreCase) {

        final String[] sectionLines = new String[section.size()];

        for (int i = 0; i < section.size(); i++) {
            final Line line = section.get(i);
            final String nextText;

            if (ignoreCase) {
                nextText = line.getText().toLowerCase();
            } else {
                nextText = line.getText();
            }
            sectionLines[i] = nextText;
        }

        for (int i = section.size() - inputs.length - 1; i >= 0; i--) {

            if (!sectionLines[i].endsWith(inputs[0])) {
                continue;
            }
            final int start = sectionLines[i].length() - inputs[0].length();

            int j = 1;
            boolean match = true;
            for (; j < inputs.length - 1; j++) {

                if (!inputs[j].equals(sectionLines[i + j])) {
                    match = false;
                    break;
                }
            }
            if (match) {
                final int startLine = startOffset + i;
                if (inputs.length == 1) {
                    return new QueryResult(startLine, startLine + 1, start, 0);
                }
                if (!sectionLines[i + j].startsWith(inputs[inputs.length - 1])) {
                    continue;
                }
                return new QueryResult(startLine, startLine + inputs.length - 1, start, inputs[j].length());
            }
        }
        return null;
    }
}

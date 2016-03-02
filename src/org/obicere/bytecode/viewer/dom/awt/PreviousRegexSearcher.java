package org.obicere.bytecode.viewer.dom.awt;

import org.obicere.bytecode.viewer.dom.Document;
import org.obicere.bytecode.viewer.dom.Line;

import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 */
public class PreviousRegexSearcher implements QuerySearcher {

    @Override
    public QueryResult search(final Document document, final Query query) {
        final String search = query.getInput();
        final Pattern pattern;

        try {
            pattern = Pattern.compile(search);
        } catch (final PatternSyntaxException e) {
            return null;
        }

        final QueryResult current = query.current();

        final int maxCount = document.getLineCount();
        final int start = (current != null ? current.getStartLine() : maxCount - 1);
        final int minimumIndex = (current != null ? current.getStart() : -1);

        final boolean ignoreCase = query.isIgnoreCase();

        // first check the line of the previous result for more results
        final Line thisLine = document.getLine(start);
        final MatchResult matcher = findInLine(thisLine, pattern, ignoreCase, minimumIndex);
        if (matcher != null) {
            return new QueryResult(start, start, matcher.start(), matcher.end());
        }

        // scan the remaining lines past the starting line
        final List<Line> lines = document.getLines(0, start);
        final QueryResult result = scan(lines, pattern, ignoreCase, 0);
        if (result != null) {
            return result;
        }

        // loop back and start from the bottom of the document
        final List<Line> loopBackLines = document.getLines(start + 1, maxCount);
        return scan(loopBackLines, pattern, ignoreCase, start + 1);
    }

    private QueryResult scan(final List<Line> lines, final Pattern pattern, final boolean ignoreCase, final int lineStart) {
        for (int i = lines.size() - 1; i >= 0; i--) {
            final Line line = lines.get(i);
            final MatchResult matcher = findInLine(line, pattern, ignoreCase, -1);
            if (matcher != null) {
                final int lineIndex = lineStart + i;
                return new QueryResult(lineIndex, lineIndex, matcher.start(), matcher.end());
            }
        }
        return null;
    }

    private MatchResult findInLine(final Line line, final Pattern pattern, final boolean ignoreCase, final int end) {
        String nextText = line.getText();
        if (ignoreCase) {
            nextText = nextText.toLowerCase();
        }

        final Matcher matcher = pattern.matcher(nextText);
        MatchResult bestResult = null;
        while (matcher.find()) {
            if (end == -1 || matcher.start() < end) {
                bestResult = matcher.toMatchResult();
            }
        }
        return bestResult;
    }
}

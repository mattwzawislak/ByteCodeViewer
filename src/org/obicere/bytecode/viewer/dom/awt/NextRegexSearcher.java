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
public class NextRegexSearcher implements QuerySearcher {

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

        final int start = (current != null ? current.getStartLine() : 0);
        final int minimumIndex = (current != null ? current.getStart() : 0);
        final int maxCount = document.getLineCount();

        final boolean ignoreCase = query.isIgnoreCase();

        // first check the line of the previous result for more results
        final Line thisLine = document.getLine(start);
        final MatchResult matcher = findInLine(thisLine, pattern, ignoreCase, minimumIndex + 1);
        if (matcher != null) {
            return new QueryResult(start, start, matcher.start(), matcher.end());
        }

        // scan the remaining lines past the starting line
        final List<Line> lines = document.getLines(start + 1, maxCount);
        final QueryResult result = scan(lines, pattern, ignoreCase, start + 1);
        if (result != null) {
            return result;
        }

        // loop back and start from the top of the document, this will
        // contain the previous result if it is indeed still valid
        final List<Line> loopBackLines = document.getLines(0, start);
        return scan(loopBackLines, pattern, ignoreCase, 0);
    }

    private QueryResult scan(final List<Line> lines, final Pattern pattern, final boolean ignoreCase, final int lineStart) {
        for (int i = 0; i < lines.size(); i++) {
            final Line line = lines.get(i);
            final MatchResult matcher = findInLine(line, pattern, ignoreCase, 0);
            if (matcher != null) {
                final int lineIndex = lineStart + i;
                return new QueryResult(lineIndex, lineIndex, matcher.start(), matcher.end());
            }
        }
        return null;
    }

    private MatchResult findInLine(final Line line, final Pattern pattern, final boolean ignoreCase, final int start) {
        String nextText = line.getText();
        if (ignoreCase) {
            nextText = nextText.toLowerCase();
        }

        final Matcher matcher = pattern.matcher(nextText);
        if (matcher.find(start)) {
            return matcher;
        } else {
            return null;
        }
    }
}

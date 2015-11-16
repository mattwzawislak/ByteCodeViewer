package org.obicere.bcviewer.dom.awt;

import org.obicere.bcviewer.dom.Document;
import org.obicere.bcviewer.dom.Line;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 */
public class Query {

    private final Document content;

    private final String input;

    private final boolean ignoreCase;

    private final boolean regex;

    private volatile QueryResult current;

    private final NextQuerySearcher nextQuerySearcher = new NextQuerySearcher();
    private final NextRegexSearcher nextRegexSearcher = new NextRegexSearcher();

    private final PreviousQuerySearcher previousQuerySearcher = new PreviousQuerySearcher();
    private final PreviousRegexSearcher previousRegexSearcher = new PreviousRegexSearcher();

    public Query(final Document content, final String input) {
        this(content, input, false, false);
    }

    public Query(final Document content, final String input, final boolean ignoreCase, final boolean regex) {
        if (content == null) {
            throw new IllegalArgumentException("cannot have content be null.");
        }
        if (input == null) {
            throw new IllegalArgumentException("cannot query on null input");
        }
        this.content = content;
        this.ignoreCase = ignoreCase;
        this.regex = regex;

        if (ignoreCase) {
            this.input = input.toLowerCase();
        } else {
            this.input = input;
        }
    }

    public String getInput() {
        return input;
    }

    public boolean isIgnoreCase() {
        return ignoreCase;
    }

    public boolean isRegex() {
        return regex;
    }

    public QueryResult current() {
        return current;
    }

    public QueryResult next() {
        if (input.equals("")) {
            return null;
        }
        final QueryResult next;
        if (input.contains("\n")) {
            // MultilineNextQuerySearcher
            next = null;
        } else {
            if (regex) {
                next = nextRegexSearcher.search(content, this);
            } else {
                next = nextQuerySearcher.search(content, this);
            }
        }
        current = next;
        return current;
    }

    public QueryResult previous() {
        if (input.equals("")) {
            return null;
        }
        final QueryResult next;
        if (input.contains("\n")) {
            // MultilinePreviousQuerySearcher
            next = null;
        } else {
            if (regex) {
                next = previousRegexSearcher.search(content, this);
            } else {
                next = previousQuerySearcher.search(content, this);
            }
        }
        current = next;
        return current;
    }

    private QueryResult queryRegex(final List<Line> lines, final int start, final int minimumIndex) {
        final Pattern pattern;
        try {
            pattern = Pattern.compile(input);
        } catch (final PatternSyntaxException e) {
            return null;
        }
        final int size = lines.size();
        for (int i = start; i < size; i++) {
            final Line line = lines.get(i);
            String text = line.getText();
            if (ignoreCase) {
                text = text.toLowerCase();
            }
            final Matcher matcher = pattern.matcher(text);

            if (matcher.find(minimumIndex)) {
                return new QueryResult(i, i, matcher.start(), matcher.end());
            }
        }
        return null;
    }

    private QueryResult queryNoLineBreaks(final List<Line> lines, final int start, final int minimumIndex) {
        final int size = lines.size();

        for (int i = start; i < size; i++) {
            final Line line = lines.get(i);
            String text = line.getText();
            if (ignoreCase) {
                text = text.toLowerCase();
            }

            final int trueMinimumIndex = (i == start) ? minimumIndex + 1 : minimumIndex;
            final int index = text.indexOf(input, trueMinimumIndex);
            if (index >= 0) {
                return new QueryResult(i, i, index, index + input.length());
            }
        }
        return null;
    }

    private QueryResult queryWithLineBreaks(final List<Line> lines, final int normal, final int start, final int minimumIndex) {
        final int size = lines.size();

        final String[] parts = input.split("\n");
        if (parts.length == 0) {
            return null;
        }

        final int startLine;
        final int endLine;

        if (normal == -1) {
            startLine = parts.length - 1;
            endLine = 0;
        } else {
            startLine = 0;
            endLine = parts.length - 1;
        }

        for (int i = start; i < size - 1; i++) {
            String text = lines.get(i).getText();
            if (ignoreCase) {
                text = text.toLowerCase();
            }

            if (!text.endsWith(parts[startLine])) {
                continue;
            }
            final int startIndex = text.length() - parts[startLine].length();

            int j = start + normal;
            boolean valid = true;
            for (; j < endLine && (j + i) < size && j >= 0; j += normal) {
                text = lines.get(j + i).getText();
                if (ignoreCase) {
                    text = text.toLowerCase();
                }
                if (!parts[j].equals(text)) {
                    valid = false;
                    break;
                }

            }
            if (!valid) {
                continue;
            }

            text = lines.get(i + j).getText();
            if (ignoreCase) {
                text = text.toLowerCase();
            }

            final String last = parts[endLine];
            if (text.startsWith(last)) {
                final int endIndex = last.length();
                return new QueryResult(i, i + j, startIndex, endIndex);
            }
        }
        return null;
    }
}

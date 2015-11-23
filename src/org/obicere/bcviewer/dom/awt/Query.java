package org.obicere.bcviewer.dom.awt;

import org.obicere.bcviewer.dom.Document;

/**
 */
public class Query {

    private final Document content;

    private final String input;

    private final boolean ignoreCase;

    private final boolean regex;

    private volatile QueryResult current;

    private final NextQuerySearcher          nextQuerySearcher          = new NextQuerySearcher();
    private final NextRegexSearcher          nextRegexSearcher          = new NextRegexSearcher();
    private final NextMultiLineQuerySearcher nextMultiLineQuerySearcher = new NextMultiLineQuerySearcher();

    private final PreviousQuerySearcher          previousQuerySearcher          = new PreviousQuerySearcher();
    private final PreviousRegexSearcher          previousRegexSearcher          = new PreviousRegexSearcher();
    private final PreviousMultiLineQuerySearcher previousMultiLineQuerySearcher = new PreviousMultiLineQuerySearcher();

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
            next = nextMultiLineQuerySearcher.search(content, this);
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
            next = previousMultiLineQuerySearcher.search(content, this);
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
}

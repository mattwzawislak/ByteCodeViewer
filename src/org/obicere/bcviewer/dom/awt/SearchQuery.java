package org.obicere.bcviewer.dom.awt;

import java.util.List;

/**
 */
public class SearchQuery {

    private int index;

    private final List<QueryResult> results;

    public SearchQuery(final List<QueryResult> results) {
        this.results = results;
    }

    public List<QueryResult> getResults() {
        return results;
    }

    public QueryResult next() {
        if (isEmpty()) {
            return null;
        }
        index = shift(1);
        return results.get(index);
    }

    public QueryResult previous() {
        if (isEmpty()) {
            return null;
        }
        index = shift(-1);
        return results.get(index);
    }

    public QueryResult start() {
        if (isEmpty()) {
            return null;
        }
        index = 0;
        return results.get(index);
    }

    public QueryResult end() {
        if (isEmpty()) {
            return null;
        }
        index = results.size() - 1;
        return results.get(index);
    }

    public boolean isEmpty() {
        return results.isEmpty();
    }

    public int size() {
        return results.size();
    }

    private int shift(final int delta) {
        final int size = size();
        int newIndex = index + delta;
        newIndex %= size;
        if (newIndex < 0) {
            newIndex = (size + newIndex) % size;
        }
        return newIndex;
    }
}

package org.obicere.bcviewer.dom.awt;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class Query {

    private QueryResultListener listener;

    private int index = 0;

    private final List<QueryResult> results;

    public Query() {
        this(new ArrayList<>());
    }

    public Query(final List<QueryResult> results) {
        this.results = results;
    }

    public void addQueryResultListener(final QueryResultListener listener) {
        if (listener == null) {
            return;
        }
        this.listener = listener;
    }

    public QueryResultListener removeQueryResultListener() {
        final QueryResultListener old = listener;
        this.listener = null;
        return old;
    }

    public void addResult(final QueryResult result) {
        results.add(result);
        if (listener != null) {
            final QueryResultEvent event = new QueryResultEvent(result, this);
            listener.queryResultAdded(event);
        }
    }

    public List<QueryResult> getResults() {
        return results;
    }

    public QueryResult current() {
        if (isEmpty()) {
            return null;
        }
        return results.get(index);
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

    public void dispose() {
        results.clear();
        listener = null;
        index = 0;

        // request the garbage collector to clear the results
        System.gc();
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

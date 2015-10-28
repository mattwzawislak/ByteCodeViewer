package org.obicere.bcviewer.dom.awt;

import java.awt.AWTEvent;

/**
 */
public class QueryResultEvent extends AWTEvent {

    private final QueryResult result;

    public QueryResultEvent(final QueryResult result, final Object source) {
        super(source, 0);

        this.result = result;
    }

    public QueryResult getResult() {
        return result;
    }
}

package org.obicere.bcviewer.dom.awt;

import org.obicere.bcviewer.dom.Document;

/**
 */
public interface QuerySearcher {

    public QueryResult search(final Document document, final Query query);

}

package org.obicere.bytecode.viewer.dom.awt;

import org.obicere.bytecode.viewer.dom.Document;

/**
 */
public interface QuerySearcher {

    public QueryResult search(final Document document, final Query query);

}

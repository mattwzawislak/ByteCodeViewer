package org.obicere.bcviewer.util;

import java.io.IOException;

/**
 * @author Obicere
 */
public interface Reader<T> {

    public T read(final IndexedDataInputStream input) throws IOException;

}

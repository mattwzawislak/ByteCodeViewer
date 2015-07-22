package org.obicere.bcviewer.util;

import java.io.DataInputStream;

/**
 * @author Obicere
 */
public class IndexedDataInputStream extends DataInputStream {

    private final IndexedByteArrayInputStream input;

    public IndexedDataInputStream(final byte[] bytes) {
        super(new IndexedByteArrayInputStream(bytes));
        input = (IndexedByteArrayInputStream) in;
    }

    public int getIndex() {
        return input.getIndex();
    }

    public void stepBack(final int offset) {
        input.stepBack(offset);
    }
}

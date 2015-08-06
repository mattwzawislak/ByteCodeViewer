package org.obicere.bcviewer.util;

import java.io.DataInputStream;

/**
 * @author Obicere
 */
public class IndexedDataInputStream extends DataInputStream {

    private int offset;

    private final IndexedByteArrayInputStream input;

    public IndexedDataInputStream(final byte[] bytes) {
        this(0, bytes);
    }

    public IndexedDataInputStream(final int offset, final byte[] bytes) {
        super(new IndexedByteArrayInputStream(bytes));
        this.offset = offset;
        this.input = (IndexedByteArrayInputStream) in;
    }

    public int getOffsetIndex() {
        return offset + input.getIndex();
    }

    public int getIndex() {
        return input.getIndex();
    }

    public void stepBack(final int offset) {
        input.stepBack(offset);
    }
}

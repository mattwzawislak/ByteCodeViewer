package org.obicere.bcviewer.util;

import java.io.ByteArrayInputStream;

/**
 * @author Obicere
 */
public class IndexedByteArrayInputStream extends ByteArrayInputStream {

    public IndexedByteArrayInputStream(final byte[] bytes) {
        super(bytes);
    }

    public IndexedByteArrayInputStream(final byte[] bytes, final int offest, final int length) {
        super(bytes, offest, length);
    }

    public int getIndex() {
        return pos;
    }

    public void stepBack(final int offset) {
        if (pos >= offset) {
            pos -= offset;
        } else {
            pos = 0;
        }
    }

}

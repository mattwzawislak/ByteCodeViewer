package org.obicere.bytecode.viewer.io;

import java.io.IOException;

/**
 */
public abstract class Source {

    public abstract byte[] read() throws IOException;

    public abstract boolean exists();

    public abstract String getPath();

    public abstract Source getSibling(final String fileName);

    @Override
    public String toString() {
        return getPath();
    }
}

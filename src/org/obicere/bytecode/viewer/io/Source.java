package org.obicere.bytecode.viewer.io;

import java.io.IOException;
import java.io.InputStream;

/**
 */
public interface Source extends AutoCloseable {

    public InputStream open() throws IOException;

    @Override
    public void close() throws IOException;

    public byte[] read() throws IOException;

    public boolean exists();

    public String getPath();

    public Source getSibling(final String fileName);
}

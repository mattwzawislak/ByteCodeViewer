package org.obicere.bytecode.viewer.io;

import java.io.IOException;

/**
 */
public interface Source {

    public byte[] read() throws IOException;

    public boolean exists();

    public String getPath();

}

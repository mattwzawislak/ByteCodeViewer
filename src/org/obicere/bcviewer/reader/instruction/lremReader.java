package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.lrem;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class lremReader implements Reader<lrem> {

    private final lrem instance = new lrem();

    @Override
    public lrem read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
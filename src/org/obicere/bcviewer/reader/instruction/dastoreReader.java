package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.dastore;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class dastoreReader implements Reader<dastore> {

    private final dastore instance = new dastore();

    @Override
    public dastore read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
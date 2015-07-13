package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.aastore;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class aastoreReader implements Reader<aastore> {

    private final aastore instance = new aastore();

    @Override
    public aastore read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}

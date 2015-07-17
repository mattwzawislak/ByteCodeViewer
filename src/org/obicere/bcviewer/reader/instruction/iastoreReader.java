package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.iastore;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class iastoreReader implements Reader<iastore> {

    private final iastore instance = new iastore();

    @Override
    public iastore read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
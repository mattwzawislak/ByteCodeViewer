package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.castore;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class castoreReader implements Reader<castore> {

    private final castore instance = new castore();

    @Override
    public castore read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}

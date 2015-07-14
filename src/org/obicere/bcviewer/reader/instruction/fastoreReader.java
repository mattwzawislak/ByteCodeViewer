package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.fastore;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class fastoreReader implements Reader<fastore> {
    @Override
    public fastore read(final IndexedDataInputStream input) throws IOException {
        return null;
    }
}
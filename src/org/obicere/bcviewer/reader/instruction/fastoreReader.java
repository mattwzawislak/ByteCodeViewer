package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.fastore;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class fastoreReader implements Reader<fastore> {

    private final fastore instance = new fastore();

    @Override
    public fastore read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.lstore;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class lstoreReader implements Reader<lstore> {

    private final lstore instance = new lstore();

    @Override
    public lstore read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
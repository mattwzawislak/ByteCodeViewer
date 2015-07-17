package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.lastore;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class lastoreReader implements Reader<lastore> {

    private final lastore instance = new lastore();

    @Override
    public lastore read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
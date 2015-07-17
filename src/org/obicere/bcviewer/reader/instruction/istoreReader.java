package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.istore;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class istoreReader implements Reader<istore> {

    private final istore instance = new istore();

    @Override
    public istore read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
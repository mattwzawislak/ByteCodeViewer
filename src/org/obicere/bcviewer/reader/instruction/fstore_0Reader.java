package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.fstore_0;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class fstore_0Reader implements Reader<fstore_0> {

    private final fstore_0 instance = new fstore_0();

    @Override
    public fstore_0 read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
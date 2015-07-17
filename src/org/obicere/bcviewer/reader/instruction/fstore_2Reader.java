package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.fstore_2;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class fstore_2Reader implements Reader<fstore_2> {

    private final fstore_2 instance = new fstore_2();

    @Override
    public fstore_2 read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
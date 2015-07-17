package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.fstore_3;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class fstore_3Reader implements Reader<fstore_3> {

    private final fstore_3 instance = new fstore_3();

    @Override
    public fstore_3 read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
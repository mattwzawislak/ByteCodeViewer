package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.fstore_1;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class fstore_1Reader implements Reader<fstore_1> {

    @Override
    public fstore_1 read(final IndexedDataInputStream input) throws IOException {
        return new fstore_1();
    }
}
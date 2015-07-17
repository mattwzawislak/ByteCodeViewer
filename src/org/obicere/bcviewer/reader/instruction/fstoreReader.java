package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.fstore;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class fstoreReader implements Reader<fstore> {

    @Override
    public fstore read(final IndexedDataInputStream input) throws IOException {
        return new fstore(input.readUnsignedByte());
    }
}
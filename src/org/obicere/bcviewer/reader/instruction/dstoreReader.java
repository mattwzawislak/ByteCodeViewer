package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.dstore;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class dstoreReader implements Reader<dstore> {

    @Override
    public dstore read(final IndexedDataInputStream input) throws IOException {
        return new dstore(input.readUnsignedByte());
    }
}
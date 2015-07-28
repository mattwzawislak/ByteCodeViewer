package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.dstore_2;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class dstore_2Reader implements Reader<dstore_2> {

    @Override
    public dstore_2 read(final IndexedDataInputStream input) throws IOException {
        return new dstore_2();
    }
}
package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.dstore_0;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class dstore_0Reader implements Reader<dstore_0> {

    private final dstore_0 instance = new dstore_0();

    @Override
    public dstore_0 read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
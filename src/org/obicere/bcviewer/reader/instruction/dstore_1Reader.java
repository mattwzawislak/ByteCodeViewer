package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.dstore_1;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class dstore_1Reader implements Reader<dstore_1> {

    private final dstore_1 instance = new dstore_1();

    @Override
    public dstore_1 read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
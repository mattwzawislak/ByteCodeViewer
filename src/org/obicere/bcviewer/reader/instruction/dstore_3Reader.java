package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.dstore_3;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class dstore_3Reader implements Reader<dstore_3> {

    private final dstore_3 instance = new dstore_3();

    @Override
    public dstore_3 read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
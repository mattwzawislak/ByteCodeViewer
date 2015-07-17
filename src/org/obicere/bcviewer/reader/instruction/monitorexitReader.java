package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.monitorexit;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class monitorexitReader implements Reader<monitorexit> {

    private final monitorexit instance = new monitorexit();

    @Override
    public monitorexit read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
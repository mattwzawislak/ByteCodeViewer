package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.lcmp;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class lcmpReader implements Reader<lcmp> {

    private final lcmp instance = new lcmp();

    @Override
    public lcmp read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.i2s;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class i2sReader implements Reader<i2s> {

    private final i2s instance = new i2s();

    @Override
    public i2s read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
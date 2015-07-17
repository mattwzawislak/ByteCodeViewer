package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.i2f;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class i2fReader implements Reader<i2f> {

    private final i2f instance = new i2f();

    @Override
    public i2f read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
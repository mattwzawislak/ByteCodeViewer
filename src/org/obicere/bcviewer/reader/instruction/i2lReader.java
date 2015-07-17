package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.i2l;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class i2lReader implements Reader<i2l> {

    private final i2l instance = new i2l();

    @Override
    public i2l read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.i2b;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class i2bReader implements Reader<i2b> {

    @Override
    public i2b read(final IndexedDataInputStream input) throws IOException {
        return new i2b();
    }
}
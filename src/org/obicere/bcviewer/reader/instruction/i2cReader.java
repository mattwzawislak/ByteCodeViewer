package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.i2c;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class i2cReader implements Reader<i2c> {
    @Override
    public i2c read(final IndexedDataInputStream input) throws IOException {
        return null;
    }
}
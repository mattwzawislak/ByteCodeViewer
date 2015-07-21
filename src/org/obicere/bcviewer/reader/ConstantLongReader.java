package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.ConstantLong;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class ConstantLongReader implements Reader<ConstantLong> {
    @Override
    public ConstantLong read(final IndexedDataInputStream input) throws IOException {
        return new ConstantLong(input.readLong());
    }
}

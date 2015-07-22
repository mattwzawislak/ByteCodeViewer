package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.IntegerElementValue;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class IntegerElementValueReader implements Reader<IntegerElementValue> {
    @Override
    public IntegerElementValue read(final IndexedDataInputStream input) throws IOException {
        return new IntegerElementValue(input.readUnsignedShort());
    }
}

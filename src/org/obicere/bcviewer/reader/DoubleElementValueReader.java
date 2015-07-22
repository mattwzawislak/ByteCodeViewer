package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.DoubleElementValue;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class DoubleElementValueReader implements Reader<DoubleElementValue> {
    @Override
    public DoubleElementValue read(final IndexedDataInputStream input) throws IOException {
        return new DoubleElementValue(input.readUnsignedShort());
    }
}

package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.ByteElementValue;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class ByteElementValueReader implements Reader<ByteElementValue> {
    @Override
    public ByteElementValue read(final IndexedDataInputStream input) throws IOException {
        return new ByteElementValue(input.readUnsignedShort());
    }
}

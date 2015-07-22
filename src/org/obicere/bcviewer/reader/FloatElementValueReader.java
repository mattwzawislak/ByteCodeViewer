package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.FloatElementValue;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class FloatElementValueReader implements Reader<FloatElementValue> {
    @Override
    public FloatElementValue read(final IndexedDataInputStream input) throws IOException {
        return new FloatElementValue(input.readUnsignedShort());
    }
}

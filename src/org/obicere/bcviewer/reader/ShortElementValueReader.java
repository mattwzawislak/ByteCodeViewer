package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.ShortElementValue;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class ShortElementValueReader implements Reader<ShortElementValue> {
    @Override
    public ShortElementValue read(final IndexedDataInputStream input) throws IOException {
        return new ShortElementValue(input.readUnsignedShort());
    }
}

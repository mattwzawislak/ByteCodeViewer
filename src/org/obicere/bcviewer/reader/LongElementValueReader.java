package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.LongElementValue;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class LongElementValueReader implements Reader<LongElementValue> {
    @Override
    public LongElementValue read(final IndexedDataInputStream input) throws IOException {
        return new LongElementValue(input.readUnsignedShort());
    }
}

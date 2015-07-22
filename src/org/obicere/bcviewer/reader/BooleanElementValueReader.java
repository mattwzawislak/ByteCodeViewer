package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.BooleanElementValue;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class BooleanElementValueReader implements Reader<BooleanElementValue> {
    @Override
    public BooleanElementValue read(final IndexedDataInputStream input) throws IOException {
        return new BooleanElementValue(input.readUnsignedShort());
    }
}

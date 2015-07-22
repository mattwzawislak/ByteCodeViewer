package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.StringElementValue;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class StringElementValueReader implements Reader<StringElementValue> {
    @Override
    public StringElementValue read(final IndexedDataInputStream input) throws IOException {
        return new StringElementValue(input.readUnsignedShort());
    }
}

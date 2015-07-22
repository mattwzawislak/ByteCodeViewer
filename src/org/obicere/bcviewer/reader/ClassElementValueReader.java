package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.ClassElementValue;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class ClassElementValueReader implements Reader<ClassElementValue> {
    @Override
    public ClassElementValue read(final IndexedDataInputStream input) throws IOException {
        return new ClassElementValue(input.readUnsignedShort());
    }
}

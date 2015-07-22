package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.EnumElementValue;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class EnumElementValueReader implements Reader<EnumElementValue> {
    @Override
    public EnumElementValue read(final IndexedDataInputStream input) throws IOException {
        final int typeNameIndex = input.readUnsignedShort();
        final int constNameIndex = input.readUnsignedShort();
        return new EnumElementValue(typeNameIndex, constNameIndex);
    }
}

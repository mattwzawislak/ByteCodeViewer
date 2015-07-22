package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.LocalVariable;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class LocalVariableReader implements Reader<LocalVariable> {
    @Override
    public LocalVariable read(final IndexedDataInputStream input) throws IOException {
        final int startPC = input.readUnsignedShort();
        final int length = input.readUnsignedShort();
        final int nameIndex = input.readUnsignedShort();
        final int descriptorIndex = input.readUnsignedShort();
        final int index = input.readUnsignedShort();

        return new LocalVariable(startPC, length, nameIndex, descriptorIndex, index);
    }
}

package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.LocalVar;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class LocalVarReader implements Reader<LocalVar> {
    @Override
    public LocalVar read(final IndexedDataInputStream input) throws IOException {
        final int startPC = input.readUnsignedShort();
        final int length = input.readUnsignedShort();
        final int index = input.readUnsignedShort();
        return new LocalVar(startPC, length, index);
    }
}

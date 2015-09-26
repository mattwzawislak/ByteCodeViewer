package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.NullVariableInfo;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 */
public class NullVariableInfoReader implements Reader<NullVariableInfo> {
    @Override
    public NullVariableInfo read(final IndexedDataInputStream input) throws IOException {
        return new NullVariableInfo(input.readUnsignedByte());
    }
}

package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.LongVariableInfo;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 */
public class LongVariableInfoReader implements Reader<LongVariableInfo> {
    @Override
    public LongVariableInfo read(final IndexedDataInputStream input) throws IOException {
        return new LongVariableInfo(input.readUnsignedByte());
    }
}

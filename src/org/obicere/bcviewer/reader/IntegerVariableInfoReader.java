package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.IntegerVariableInfo;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 */
public class IntegerVariableInfoReader implements Reader<IntegerVariableInfo> {

    @Override
    public IntegerVariableInfo read(final IndexedDataInputStream input) throws IOException {
        return new IntegerVariableInfo(input.readUnsignedByte());
    }
}

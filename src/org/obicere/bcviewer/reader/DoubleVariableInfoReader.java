package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.DoubleVariableInfo;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 */
public class DoubleVariableInfoReader implements Reader<DoubleVariableInfo> {
    @Override
    public DoubleVariableInfo read(final IndexedDataInputStream input) throws IOException {
        return new DoubleVariableInfo(input.readUnsignedByte());
    }
}

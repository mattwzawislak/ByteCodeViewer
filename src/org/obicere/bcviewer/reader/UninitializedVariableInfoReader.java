package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.UninitializedVariableInfo;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 */
public class UninitializedVariableInfoReader implements Reader<UninitializedVariableInfo> {
    @Override
    public UninitializedVariableInfo read(final IndexedDataInputStream input) throws IOException {
        return new UninitializedVariableInfo(input.readUnsignedByte(), input.readUnsignedShort());
    }
}

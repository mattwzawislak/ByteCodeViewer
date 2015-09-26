package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.UninitializedThisVariableInfo;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 */
public class UninitializedThisVariableInfoReader implements Reader<UninitializedThisVariableInfo> {
    @Override
    public UninitializedThisVariableInfo read(final IndexedDataInputStream input) throws IOException {
        return new UninitializedThisVariableInfo(input.readUnsignedByte());
    }
}

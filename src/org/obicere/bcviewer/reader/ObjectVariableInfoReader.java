package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.ObjectVariableInfo;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 */
public class ObjectVariableInfoReader implements Reader<ObjectVariableInfo> {
    @Override
    public ObjectVariableInfo read(final IndexedDataInputStream input) throws IOException {
        return new ObjectVariableInfo(input.readUnsignedByte(), input.readUnsignedShort());
    }
}

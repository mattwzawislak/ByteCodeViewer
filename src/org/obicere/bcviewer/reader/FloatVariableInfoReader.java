package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.FloatVariableInfo;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 */
public class FloatVariableInfoReader implements Reader<FloatVariableInfo> {
    @Override
    public FloatVariableInfo read(final IndexedDataInputStream input) throws IOException {
        return new FloatVariableInfo(input.readUnsignedByte());
    }
}

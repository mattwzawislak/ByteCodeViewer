package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.ConstantString;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class ConstantStringReader implements Reader<ConstantString> {
    @Override
    public ConstantString read(final IndexedDataInputStream input) throws IOException {
        return new ConstantString(input.readUnsignedShort());
    }
}

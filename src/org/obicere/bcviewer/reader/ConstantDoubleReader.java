package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.ConstantDouble;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class ConstantDoubleReader implements Reader<ConstantDouble> {
    @Override
    public ConstantDouble read(final IndexedDataInputStream input) throws IOException {
        return new ConstantDouble(input.readDouble());
    }
}

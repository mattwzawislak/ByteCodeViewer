package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.ConstantFloat;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class ConstantFloatReader implements Reader<ConstantFloat> {
    @Override
    public ConstantFloat read(final IndexedDataInputStream input) throws IOException {
        return new ConstantFloat(input.readFloat());
    }
}

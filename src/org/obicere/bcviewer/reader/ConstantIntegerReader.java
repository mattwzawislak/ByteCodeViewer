package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.ConstantInteger;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class ConstantIntegerReader implements Reader<ConstantInteger> {
    @Override
    public ConstantInteger read(final IndexedDataInputStream input) throws IOException {
        return new ConstantInteger(input.readInt());
    }
}

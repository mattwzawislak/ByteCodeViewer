package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.ConstantClass;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class ConstantClassReader implements Reader<ConstantClass> {
    @Override
    public ConstantClass read(final IndexedDataInputStream input) throws IOException {
        return new ConstantClass(input.readUnsignedShort());
    }
}

package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.ConstantMethodRef;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class ConstantMethodRefReader implements Reader<ConstantMethodRef> {
    @Override
    public ConstantMethodRef read(final IndexedDataInputStream input) throws IOException {
        return new ConstantMethodRef(input.readUnsignedShort(), input.readUnsignedShort());
    }
}

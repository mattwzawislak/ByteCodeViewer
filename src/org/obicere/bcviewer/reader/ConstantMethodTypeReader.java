package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.ConstantMethodType;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class ConstantMethodTypeReader implements Reader<ConstantMethodType> {
    @Override
    public ConstantMethodType read(final IndexedDataInputStream input) throws IOException {
        return new ConstantMethodType(input.readUnsignedShort());
    }
}

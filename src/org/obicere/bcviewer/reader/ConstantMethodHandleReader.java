package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.ConstantMethodHandle;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class ConstantMethodHandleReader implements Reader<ConstantMethodHandle> {
    @Override
    public ConstantMethodHandle read(final IndexedDataInputStream input) throws IOException {
        return new ConstantMethodHandle(input.readByte(), input.readUnsignedShort());
    }
}

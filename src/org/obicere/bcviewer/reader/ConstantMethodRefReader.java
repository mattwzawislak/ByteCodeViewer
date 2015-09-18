package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.ConstantMethodRef_;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class ConstantMethodRefReader implements Reader<ConstantMethodRef_> {
    @Override
    public ConstantMethodRef_ read(final IndexedDataInputStream input) throws IOException {
        return new ConstantMethodRef_(input.readUnsignedShort(), input.readUnsignedShort());
    }
}

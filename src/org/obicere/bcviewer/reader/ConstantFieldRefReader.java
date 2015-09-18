package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.ConstantFieldRef_;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class ConstantFieldRefReader implements Reader<ConstantFieldRef_> {
    @Override
    public ConstantFieldRef_ read(final IndexedDataInputStream input) throws IOException {
        return new ConstantFieldRef_(input.readUnsignedShort(), input.readUnsignedShort());
    }
}

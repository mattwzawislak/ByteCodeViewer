package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.ConstantFieldRef;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class ConstantFieldRefReader implements Reader<ConstantFieldRef> {
    @Override
    public ConstantFieldRef read(final IndexedDataInputStream input) throws IOException {
        return new ConstantFieldRef(input.readUnsignedShort(), input.readUnsignedShort());
    }
}

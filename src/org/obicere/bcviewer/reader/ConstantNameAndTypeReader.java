package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.ConstantNameAndType;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class ConstantNameAndTypeReader implements Reader<ConstantNameAndType> {
    @Override
    public ConstantNameAndType read(final IndexedDataInputStream input) throws IOException {
        return new ConstantNameAndType(input.readUnsignedShort(), input.readUnsignedShort());
    }
}

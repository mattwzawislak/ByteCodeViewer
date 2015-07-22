package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.ConstantValueAttribute;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class ConstantValueAttributeReader implements Reader<ConstantValueAttribute> {
    @Override
    public ConstantValueAttribute read(final IndexedDataInputStream input) throws IOException {
        return new ConstantValueAttribute(input.readUnsignedShort());
    }
}

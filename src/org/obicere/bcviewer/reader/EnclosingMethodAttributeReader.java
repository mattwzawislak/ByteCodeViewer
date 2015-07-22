package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.EnclosingMethodAttribute;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class EnclosingMethodAttributeReader implements Reader<EnclosingMethodAttribute> {
    @Override
    public EnclosingMethodAttribute read(final IndexedDataInputStream input) throws IOException {
        final int classIndex = input.readUnsignedShort();
        final int methodIndex = input.readUnsignedShort();
        return new EnclosingMethodAttribute(classIndex, methodIndex);
    }
}

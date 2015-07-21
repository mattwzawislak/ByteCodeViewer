package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.InnerClassesAttribute;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class InnerClassesAttributeReader implements Reader<InnerClassesAttribute> {
    @Override
    public InnerClassesAttribute read(final IndexedDataInputStream input) throws IOException {
        return null;
    }
}

package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.DeprecatedAttribute;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class DeprecatedAttributeReader implements Reader<DeprecatedAttribute> {
    @Override
    public DeprecatedAttribute read(final IndexedDataInputStream input) throws IOException {
        return null;
    }
}

package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.RuntimeInvisibleAnnotationsAttribute;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class RuntimeInvisibleAnnotationsAttributeReader implements Reader<RuntimeInvisibleAnnotationsAttribute> {
    @Override
    public RuntimeInvisibleAnnotationsAttribute read(final IndexedDataInputStream input) throws IOException {
        return null;
    }
}

package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.RuntimeInvisibleTypeAnnotationsAttribute;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class RuntimeInvisibleTypeAnnotationsAttributeReader implements Reader<RuntimeInvisibleTypeAnnotationsAttribute> {
    @Override
    public RuntimeInvisibleTypeAnnotationsAttribute read(final IndexedDataInputStream input) throws IOException {
        return null;
    }
}

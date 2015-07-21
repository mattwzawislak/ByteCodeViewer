package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.SourceDebugExtensionAttribute;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class SourceDebugExtensionAttributeReader implements Reader<SourceDebugExtensionAttribute> {
    @Override
    public SourceDebugExtensionAttribute read(final IndexedDataInputStream input) throws IOException {
        return null;
    }
}

package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.BootstrapMethodsAttribute;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class BootstrapMethodsAttributeReader implements Reader<BootstrapMethodsAttribute> {
    @Override
    public BootstrapMethodsAttribute read(final IndexedDataInputStream input) throws IOException {
        return null;
    }
}

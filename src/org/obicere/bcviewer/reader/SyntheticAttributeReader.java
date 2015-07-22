package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.SyntheticAttribute;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class SyntheticAttributeReader implements Reader<SyntheticAttribute> {

    private final SyntheticAttribute instance = new SyntheticAttribute();

    @Override
    public SyntheticAttribute read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}

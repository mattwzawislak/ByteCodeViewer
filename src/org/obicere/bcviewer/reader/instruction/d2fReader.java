package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.d2f;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class d2fReader implements Reader<d2f> {

    @Override
    public d2f read(final IndexedDataInputStream input) throws IOException {
        return new d2f();
    }
}

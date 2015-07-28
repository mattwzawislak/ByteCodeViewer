package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.d2i;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class d2iReader implements Reader<d2i> {

    @Override
    public d2i read(final IndexedDataInputStream input) throws IOException {
        return new d2i();
    }
}
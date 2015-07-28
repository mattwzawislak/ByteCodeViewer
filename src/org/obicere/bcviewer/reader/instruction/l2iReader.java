package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.l2i;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class l2iReader implements Reader<l2i> {

    @Override
    public l2i read(final IndexedDataInputStream input) throws IOException {
        return new l2i();
    }
}
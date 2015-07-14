package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.ret;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class retReader implements Reader<ret> {
    @Override
    public ret read(final IndexedDataInputStream input) throws IOException {
        return null;
    }
}
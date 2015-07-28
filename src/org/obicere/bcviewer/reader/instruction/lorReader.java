package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.lor;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class lorReader implements Reader<lor> {

    @Override
    public lor read(final IndexedDataInputStream input) throws IOException {
        return new lor();
    }
}
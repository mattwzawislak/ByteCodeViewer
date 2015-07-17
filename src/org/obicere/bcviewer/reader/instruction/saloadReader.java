package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.saload;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class saloadReader implements Reader<saload> {

    private final saload instance = new saload();

    @Override
    public saload read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
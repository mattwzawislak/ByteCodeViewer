package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.lload;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class lloadReader implements Reader<lload> {

    private final lload instance = new lload();

    @Override
    public lload read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
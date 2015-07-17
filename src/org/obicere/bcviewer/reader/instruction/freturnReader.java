package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.freturn;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class freturnReader implements Reader<freturn> {

    private final freturn instance = new freturn();

    @Override
    public freturn read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
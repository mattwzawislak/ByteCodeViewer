package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.iadd;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class iaddReader implements Reader<iadd> {

    private final iadd instance = new iadd();

    @Override
    public iadd read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.swap;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class swapReader implements Reader<swap> {

    private final swap instance = new swap();

    @Override
    public swap read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
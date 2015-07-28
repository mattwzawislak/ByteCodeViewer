package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.swap;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class swapReader implements Reader<swap> {

    @Override
    public swap read(final IndexedDataInputStream input) throws IOException {
        return new swap();
    }
}
package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.dadd;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class daddReader implements Reader<dadd> {
    @Override
    public dadd read(final IndexedDataInputStream input) throws IOException {
        return null;
    }
}
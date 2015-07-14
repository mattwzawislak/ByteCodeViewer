package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.ifne;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class ifneReader implements Reader<ifne> {
    @Override
    public ifne read(final IndexedDataInputStream input) throws IOException {
        return null;
    }
}
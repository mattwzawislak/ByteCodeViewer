package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.fload;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class floadReader implements Reader<fload> {
    @Override
    public fload read(final IndexedDataInputStream input) throws IOException {
        return null;
    }
}
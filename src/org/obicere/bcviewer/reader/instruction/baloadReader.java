package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.baload;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class baloadReader implements Reader<baload> {

    @Override
    public baload read(final IndexedDataInputStream input) throws IOException {
        return new baload();
    }
}

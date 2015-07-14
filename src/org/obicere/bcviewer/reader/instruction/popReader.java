package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.pop;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class popReader implements Reader<pop> {
    @Override
    public pop read(final IndexedDataInputStream input) throws IOException {
        return null;
    }
}
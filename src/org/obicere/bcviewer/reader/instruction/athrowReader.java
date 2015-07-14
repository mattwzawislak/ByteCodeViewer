package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.athrow;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class athrowReader implements Reader<athrow> {

    private final athrow instance = new athrow();

    @Override
    public athrow read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}

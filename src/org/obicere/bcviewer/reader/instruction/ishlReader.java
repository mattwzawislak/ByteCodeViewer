package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.ishl;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class ishlReader implements Reader<ishl> {

    private final ishl instance = new ishl();

    @Override
    public ishl read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
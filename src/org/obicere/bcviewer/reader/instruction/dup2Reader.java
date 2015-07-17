package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.dup2;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class dup2Reader implements Reader<dup2> {

    private final dup2 instance = new dup2();

    @Override
    public dup2 read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.dup2_x2;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
*/
public class dup2_x2Reader implements Reader<dup2_x2> {

    private final dup2_x2 instance = new dup2_x2();

    @Override
    public dup2_x2 read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
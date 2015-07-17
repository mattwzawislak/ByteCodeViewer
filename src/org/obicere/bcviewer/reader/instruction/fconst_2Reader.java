package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.fconst_2;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class fconst_2Reader implements Reader<fconst_2> {

    private final fconst_2 instance = new fconst_2();

    @Override
    public fconst_2 read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
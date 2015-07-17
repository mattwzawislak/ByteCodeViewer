package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.fconst_0;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class fconst_0Reader implements Reader<fconst_0> {

    private final fconst_0 instance = new fconst_0();

    @Override
    public fconst_0 read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
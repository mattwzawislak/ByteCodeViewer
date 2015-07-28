package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.fmul;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class fmulReader implements Reader<fmul> {

    @Override
    public fmul read(final IndexedDataInputStream input) throws IOException {
        return new fmul();
    }
}
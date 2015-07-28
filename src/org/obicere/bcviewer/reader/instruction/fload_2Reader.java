package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.fload_2;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class fload_2Reader implements Reader<fload_2> {

    @Override
    public fload_2 read(final IndexedDataInputStream input) throws IOException {
        return new fload_2();
    }
}
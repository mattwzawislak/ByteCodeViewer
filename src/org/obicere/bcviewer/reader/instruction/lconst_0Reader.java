package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.lconst_0;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class lconst_0Reader implements Reader<lconst_0> {

    @Override
    public lconst_0 read(final IndexedDataInputStream input) throws IOException {
        return new lconst_0();
    }
}
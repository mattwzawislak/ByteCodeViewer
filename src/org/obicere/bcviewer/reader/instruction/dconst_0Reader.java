package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.dconst_0;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class dconst_0Reader implements Reader<dconst_0> {

    private final dconst_0 instance = new dconst_0();

    @Override
    public dconst_0 read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
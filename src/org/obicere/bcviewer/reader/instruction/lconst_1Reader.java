package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.lconst_1;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class lconst_1Reader implements Reader<lconst_1> {

    private final lconst_1 instance = new lconst_1();

    @Override
    public lconst_1 read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.istore_1;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class istore_1Reader implements Reader<istore_1> {

    private final istore_1 instance = new istore_1();

    @Override
    public istore_1 read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
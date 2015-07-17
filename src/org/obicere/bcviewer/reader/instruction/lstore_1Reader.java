package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.lstore_1;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class lstore_1Reader implements Reader<lstore_1> {

    private final lstore_1 instance = new lstore_1();

    @Override
    public lstore_1 read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
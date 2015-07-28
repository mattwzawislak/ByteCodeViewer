package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.lstore_2;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class lstore_2Reader implements Reader<lstore_2> {

    @Override
    public lstore_2 read(final IndexedDataInputStream input) throws IOException {
        return new lstore_2();
    }
}
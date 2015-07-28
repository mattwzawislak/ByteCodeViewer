package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.lstore_0;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class lstore_0Reader implements Reader<lstore_0> {

    @Override
    public lstore_0 read(final IndexedDataInputStream input) throws IOException {
        return new lstore_0();
    }
}
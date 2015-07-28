package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.istore_0;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class istore_0Reader implements Reader<istore_0> {

    @Override
    public istore_0 read(final IndexedDataInputStream input) throws IOException {
        return new istore_0();
    }
}
package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.istore_2;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class istore_2Reader implements Reader<istore_2> {

    @Override
    public istore_2 read(final IndexedDataInputStream input) throws IOException {
        return new istore_2();
    }
}
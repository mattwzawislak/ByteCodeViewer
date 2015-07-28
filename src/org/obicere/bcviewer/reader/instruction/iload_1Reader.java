package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.iload_1;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class iload_1Reader implements Reader<iload_1> {

    @Override
    public iload_1 read(final IndexedDataInputStream input) throws IOException {
        return new iload_1();
    }
}
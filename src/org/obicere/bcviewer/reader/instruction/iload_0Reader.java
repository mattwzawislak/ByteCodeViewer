package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.iload_0;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class iload_0Reader implements Reader<iload_0> {

    private final iload_0 instance = new iload_0();

    @Override
    public iload_0 read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
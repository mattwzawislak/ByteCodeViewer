package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.iload_2;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class iload_2Reader implements Reader<iload_2> {

    private final iload_2 instance = new iload_2();

    @Override
    public iload_2 read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
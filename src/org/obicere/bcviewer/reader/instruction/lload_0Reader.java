package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.lload_0;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class lload_0Reader implements Reader<lload_0> {

    private final lload_0 instance = new lload_0();

    @Override
    public lload_0 read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
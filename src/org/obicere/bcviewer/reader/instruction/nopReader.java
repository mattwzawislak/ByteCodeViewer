package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.nop;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class nopReader implements Reader<nop> {

    private final nop instance = new nop();

    @Override
    public nop read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
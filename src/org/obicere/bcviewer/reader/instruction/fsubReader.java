package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.fsub;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class fsubReader implements Reader<fsub> {

    private final fsub instance = new fsub();

    @Override
    public fsub read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.isub;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class isubReader implements Reader<isub> {

    private final isub instance = new isub();

    @Override
    public isub read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
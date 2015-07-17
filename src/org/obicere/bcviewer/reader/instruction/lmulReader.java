package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.lmul;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class lmulReader implements Reader<lmul> {

    private final lmul instance = new lmul();

    @Override
    public lmul read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
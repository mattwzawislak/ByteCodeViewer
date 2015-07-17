package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.lreturn;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class lreturnReader implements Reader<lreturn> {

    private final lreturn instance = new lreturn();

    @Override
    public lreturn read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.ishr;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class ishrReader implements Reader<ishr> {

    private final ishr instance = new ishr();

    @Override
    public ishr read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
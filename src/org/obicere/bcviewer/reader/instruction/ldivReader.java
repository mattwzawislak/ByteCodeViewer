package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.ldiv;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class ldivReader implements Reader<ldiv> {

    private final ldiv instance = new ldiv();

    @Override
    public ldiv read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.ddiv;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class ddivReader implements Reader<ddiv> {

    private final ddiv instance = new ddiv();

    @Override
    public ddiv read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
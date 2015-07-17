package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.fdiv;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class fdivReader implements Reader<fdiv> {

    private final fdiv instance = new fdiv();

    @Override
    public fdiv read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.drem;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class dremReader implements Reader<drem> {

    private final drem instance = new drem();

    @Override
    public drem read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
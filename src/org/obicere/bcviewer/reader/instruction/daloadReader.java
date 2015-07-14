package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.daload;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class daloadReader implements Reader<daload> {

    private final daload instance = new daload();

    @Override
    public daload read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
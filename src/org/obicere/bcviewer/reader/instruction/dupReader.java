package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.dup;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class dupReader implements Reader<dup> {
    @Override
    public dup read(final IndexedDataInputStream input) throws IOException {
        return null;
    }
}
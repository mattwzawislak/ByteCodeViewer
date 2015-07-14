package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.iaload;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class ialoadReader implements Reader<iaload> {
    @Override
    public iaload read(final IndexedDataInputStream input) throws IOException {
        return null;
    }
}
package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.iload;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class iloadReader implements Reader<iload> {

    private final iload instance = new iload();

    @Override
    public iload read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
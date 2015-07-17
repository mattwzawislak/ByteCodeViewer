package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.sipush;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class sipushReader implements Reader<sipush> {

    private final sipush instance = new sipush();

    @Override
    public sipush read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
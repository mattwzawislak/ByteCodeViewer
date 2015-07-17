package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.wide;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class wideReader implements Reader<wide> {

    private final wide instance = new wide();

    @Override
    public wide read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
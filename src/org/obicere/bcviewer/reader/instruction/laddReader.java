package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.ladd;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class laddReader implements Reader<ladd> {

    @Override
    public ladd read(final IndexedDataInputStream input) throws IOException {
        return new ladd();
    }
}
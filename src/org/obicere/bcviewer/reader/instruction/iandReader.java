package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.iand;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class iandReader implements Reader<iand> {

    private final iand instance = new iand();

    @Override
    public iand read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
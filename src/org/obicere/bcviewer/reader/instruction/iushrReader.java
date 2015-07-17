package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.iushr;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class iushrReader implements Reader<iushr> {

    private final iushr instance = new iushr();

    @Override
    public iushr read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
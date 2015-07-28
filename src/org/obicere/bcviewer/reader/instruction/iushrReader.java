package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.iushr;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class iushrReader implements Reader<iushr> {

    @Override
    public iushr read(final IndexedDataInputStream input) throws IOException {
        return new iushr();
    }
}
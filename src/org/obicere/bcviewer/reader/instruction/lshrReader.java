package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.lshr;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class lshrReader implements Reader<lshr> {

    @Override
    public lshr read(final IndexedDataInputStream input) throws IOException {
        return new lshr();
    }
}
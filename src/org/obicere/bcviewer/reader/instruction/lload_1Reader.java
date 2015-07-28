package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.lload_1;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class lload_1Reader implements Reader<lload_1> {

    @Override
    public lload_1 read(final IndexedDataInputStream input) throws IOException {
        return new lload_1();
    }
}
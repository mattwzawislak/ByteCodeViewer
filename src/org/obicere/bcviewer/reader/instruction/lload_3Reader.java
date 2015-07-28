package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.lload_3;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class lload_3Reader implements Reader<lload_3> {

    @Override
    public lload_3 read(final IndexedDataInputStream input) throws IOException {
        return new lload_3();
    }
}
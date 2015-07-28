package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.laload;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class laloadReader implements Reader<laload> {

    @Override
    public laload read(final IndexedDataInputStream input) throws IOException {
        return new laload();
    }
}
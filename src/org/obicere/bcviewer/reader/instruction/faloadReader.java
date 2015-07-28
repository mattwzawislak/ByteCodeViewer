package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.faload;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class faloadReader implements Reader<faload> {

    @Override
    public faload read(final IndexedDataInputStream input) throws IOException {
        return new faload();
    }
}
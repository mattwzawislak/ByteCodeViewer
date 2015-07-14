package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.ifnull;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class ifnullReader implements Reader<ifnull> {
    @Override
    public ifnull read(final IndexedDataInputStream input) throws IOException {
        return null;
    }
}
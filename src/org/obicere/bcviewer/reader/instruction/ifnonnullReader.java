package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.ifnonnull;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class ifnonnullReader implements Reader<ifnonnull> {
    @Override
    public ifnonnull read(final IndexedDataInputStream input) throws IOException {
        return null;
    }
}
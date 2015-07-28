package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.ior;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class iorReader implements Reader<ior> {

    @Override
    public ior read(final IndexedDataInputStream input) throws IOException {
        return new ior();
    }
}
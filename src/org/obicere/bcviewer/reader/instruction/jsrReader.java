package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.jsr;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class jsrReader implements Reader<jsr> {
    @Override
    public jsr read(final IndexedDataInputStream input) throws IOException {
        return null;
    }
}
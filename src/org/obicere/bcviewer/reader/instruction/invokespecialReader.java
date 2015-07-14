package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.invokespecial;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class invokespecialReader implements Reader<invokespecial> {
    @Override
    public invokespecial read(final IndexedDataInputStream input) throws IOException {
        return null;
    }
}
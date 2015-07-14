package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.dload;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class dloadReader implements Reader<dload> {
    @Override
    public dload read(final IndexedDataInputStream input) throws IOException {
        return null;
    }
}
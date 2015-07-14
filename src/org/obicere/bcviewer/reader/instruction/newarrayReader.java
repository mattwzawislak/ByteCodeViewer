package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.newarray;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class newarrayReader implements Reader<newarray> {
    @Override
    public newarray read(final IndexedDataInputStream input) throws IOException {
        return null;
    }
}
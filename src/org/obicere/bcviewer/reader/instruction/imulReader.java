package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.imul;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class imulReader implements Reader<imul> {

    private final imul instance = new imul();

    @Override
    public imul read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
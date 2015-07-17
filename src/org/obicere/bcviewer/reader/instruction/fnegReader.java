package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.fneg;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class fnegReader implements Reader<fneg> {

    private final fneg instance = new fneg();

    @Override
    public fneg read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
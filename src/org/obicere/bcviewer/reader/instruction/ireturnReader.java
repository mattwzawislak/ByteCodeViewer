package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.ireturn;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class ireturnReader implements Reader<ireturn> {

    private final ireturn instance = new ireturn();

    @Override
    public ireturn read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
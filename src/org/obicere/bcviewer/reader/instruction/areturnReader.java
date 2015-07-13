package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.areturn;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class areturnReader implements Reader<areturn> {

    private final areturn instance = new areturn();

    @Override
    public areturn read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}

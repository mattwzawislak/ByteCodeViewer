package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.caload;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class caloadReader implements Reader<caload> {

    private final caload instance = new caload();

    @Override
    public caload read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}

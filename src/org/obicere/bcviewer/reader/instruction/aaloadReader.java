package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.aaload;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class aaloadReader implements Reader<aaload> {

    @Override
    public aaload read(final IndexedDataInputStream input) throws IOException {
        return new aaload();
    }
}

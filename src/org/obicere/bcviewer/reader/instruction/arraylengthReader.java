package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.arraylength;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class arraylengthReader implements Reader<arraylength> {

    @Override
    public arraylength read(final IndexedDataInputStream input) throws IOException {
        return new arraylength();
    }
}

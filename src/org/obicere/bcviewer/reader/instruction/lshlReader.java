package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.lshl;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class lshlReader implements Reader<lshl> {

    @Override
    public lshl read(final IndexedDataInputStream input) throws IOException {
        return new lshl();
    }
}
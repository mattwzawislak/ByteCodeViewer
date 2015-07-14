package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.dneg;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class dnegReader implements Reader<dneg> {
    @Override
    public dneg read(final IndexedDataInputStream input) throws IOException {
        return null;
    }
}
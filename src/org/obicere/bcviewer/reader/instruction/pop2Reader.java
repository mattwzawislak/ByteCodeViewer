package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.pop2;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class pop2Reader implements Reader<pop2> {

    @Override
    public pop2 read(final IndexedDataInputStream input) throws IOException {
        return new pop2();
    }
}
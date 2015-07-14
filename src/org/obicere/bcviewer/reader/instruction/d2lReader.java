package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.d2l;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class d2lReader implements Reader<d2l> {

    private final d2l instance = new d2l();

    @Override
    public d2l read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
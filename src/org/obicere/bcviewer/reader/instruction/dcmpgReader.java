package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.dcmpg;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class dcmpgReader implements Reader<dcmpg> {

    private final dcmpg instance = new dcmpg();

    @Override
    public dcmpg read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
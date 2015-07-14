package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.dload_0;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class dload_0Reader implements Reader<dload_0> {

    private final dload_0 instance = new dload_0();

    @Override
    public dload_0 read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
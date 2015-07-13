package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.aload_0;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class aload_0Reader implements Reader<aload_0> {

    private final aload_0 instance = new aload_0();

    @Override
    public aload_0 read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}

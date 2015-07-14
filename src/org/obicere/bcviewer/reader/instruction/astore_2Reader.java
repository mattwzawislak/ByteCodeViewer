package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.astore_2;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class astore_2Reader implements Reader<astore_2> {

    private final astore_2 instance = new astore_2();

    @Override
    public astore_2 read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}

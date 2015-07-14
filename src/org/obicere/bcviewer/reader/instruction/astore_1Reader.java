package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.astore_1;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class astore_1Reader implements Reader<astore_1> {

    private final astore_1 instance = new astore_1();

    @Override
    public astore_1 read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}

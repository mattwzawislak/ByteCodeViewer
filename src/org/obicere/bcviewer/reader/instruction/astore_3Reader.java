package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.astore_3;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class astore_3Reader implements Reader<astore_3> {

    private final astore_3 instance = new astore_3();

    @Override
    public astore_3 read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}

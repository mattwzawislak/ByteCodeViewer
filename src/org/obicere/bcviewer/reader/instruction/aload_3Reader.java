package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.aload_3;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class aload_3Reader implements Reader<aload_3> {

    private final aload_3 instance = new aload_3();

    @Override
    public aload_3 read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
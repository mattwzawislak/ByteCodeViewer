package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.dload_3;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class dload_3Reader implements Reader<dload_3> {

    private final dload_3 instance = new dload_3();

    @Override
    public dload_3 read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
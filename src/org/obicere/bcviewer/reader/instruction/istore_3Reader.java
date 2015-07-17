package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.istore_3;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class istore_3Reader implements Reader<istore_3> {

    private final istore_3 instance = new istore_3();

    @Override
    public istore_3 read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
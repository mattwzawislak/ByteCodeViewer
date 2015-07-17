package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.instanceof_;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class instanceof_Reader implements Reader<instanceof_> {

    private final instanceof_ instance = new instanceof_();

    @Override
    public instanceof_ read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
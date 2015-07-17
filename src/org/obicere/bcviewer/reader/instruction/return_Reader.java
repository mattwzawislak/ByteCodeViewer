package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.return_;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class return_Reader implements Reader<return_> {

    private final return_ instance = new return_();

    @Override
    public return_ read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
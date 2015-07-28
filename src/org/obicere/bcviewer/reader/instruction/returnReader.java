package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.return_;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class returnReader implements Reader<return_> {

    @Override
    public return_ read(final IndexedDataInputStream input) throws IOException {
        return new return_();
    }
}
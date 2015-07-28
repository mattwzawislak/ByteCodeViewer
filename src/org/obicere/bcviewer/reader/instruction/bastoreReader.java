package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.bastore;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class bastoreReader implements Reader<bastore> {

    @Override
    public bastore read(final IndexedDataInputStream input) throws IOException {
        return new bastore();
    }
}

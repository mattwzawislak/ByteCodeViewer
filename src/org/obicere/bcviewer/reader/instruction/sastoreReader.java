package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.sastore;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class sastoreReader implements Reader<sastore> {

    @Override
    public sastore read(final IndexedDataInputStream input) throws IOException {
        return new sastore();
    }
}
package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.aconst_null;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class aconst_nullReader implements Reader<aconst_null> {

    @Override
    public aconst_null read(final IndexedDataInputStream input) throws IOException {
        return new aconst_null();
    }
}

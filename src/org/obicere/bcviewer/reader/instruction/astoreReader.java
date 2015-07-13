package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.astore;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class astoreReader implements Reader<astore> {

    @Override
    public astore read(final IndexedDataInputStream input) throws IOException {
        return new astore(input.readUnsignedByte());
    }
}

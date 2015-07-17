package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.ldc;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class ldcReader implements Reader<ldc> {

    @Override
    public ldc read(final IndexedDataInputStream input) throws IOException {
        return new ldc(input.readUnsignedByte());
    }
}
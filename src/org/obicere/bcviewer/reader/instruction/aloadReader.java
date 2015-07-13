package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.aload;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class aloadReader implements Reader<aload> {
    @Override
    public aload read(final IndexedDataInputStream input) throws IOException {
        return new aload(input.readUnsignedByte());
    }
}

package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.bipush;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class bipushReader implements Reader<bipush> {

    @Override
    public bipush read(final IndexedDataInputStream input) throws IOException {
        return new bipush(input.readByte());
    }
}

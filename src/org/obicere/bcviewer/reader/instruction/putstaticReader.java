package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.putstatic;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class putstaticReader implements Reader<putstatic> {

    @Override
    public putstatic read(final IndexedDataInputStream input) throws IOException {
        return new putstatic(input.readUnsignedByte(), input.readUnsignedByte());
    }
}
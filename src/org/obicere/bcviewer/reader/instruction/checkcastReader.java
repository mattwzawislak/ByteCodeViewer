package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.checkcast;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class checkcastReader implements Reader<checkcast> {

    @Override
    public checkcast read(final IndexedDataInputStream input) throws IOException {
        return new checkcast(input.readUnsignedByte(), input.readUnsignedByte());
    }
}

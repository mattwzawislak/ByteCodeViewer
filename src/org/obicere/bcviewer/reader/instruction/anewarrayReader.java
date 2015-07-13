package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.anewarray;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class anewarrayReader implements Reader<anewarray> {

    @Override
    public anewarray read(final IndexedDataInputStream input) throws IOException {
        return new anewarray(input.readUnsignedByte(), input.readUnsignedByte());
    }
}

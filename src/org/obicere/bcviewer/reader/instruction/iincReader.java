package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.iinc;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class iincReader implements Reader<iinc> {

    @Override
    public iinc read(final IndexedDataInputStream input) throws IOException {
        return new iinc(input.readUnsignedByte(), input.readUnsignedByte());
    }
}
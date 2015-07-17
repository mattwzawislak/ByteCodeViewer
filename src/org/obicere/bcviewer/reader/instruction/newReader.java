package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.new_;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class newReader implements Reader<new_> {

    @Override
    public new_ read(final IndexedDataInputStream input) throws IOException {
        return new new_(input.readUnsignedByte(), input.readUnsignedByte());
    }
}
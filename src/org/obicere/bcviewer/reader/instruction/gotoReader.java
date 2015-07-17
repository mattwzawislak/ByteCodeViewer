package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.goto_;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class gotoReader implements Reader<goto_> {

    @Override
    public goto_ read(final IndexedDataInputStream input) throws IOException {
        return new goto_(input.readUnsignedByte(), input.readUnsignedByte());
    }
}
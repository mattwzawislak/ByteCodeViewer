package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.getfield;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class getfieldReader implements Reader<getfield> {

    @Override
    public getfield read(final IndexedDataInputStream input) throws IOException {
        return new getfield(input.readUnsignedByte(), input.readUnsignedByte());
    }
}
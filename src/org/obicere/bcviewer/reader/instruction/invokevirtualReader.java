package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.invokevirtual;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class invokevirtualReader implements Reader<invokevirtual> {

    @Override
    public invokevirtual read(final IndexedDataInputStream input) throws IOException {
        return new invokevirtual(input.readUnsignedByte(), input.readUnsignedByte());
    }
}
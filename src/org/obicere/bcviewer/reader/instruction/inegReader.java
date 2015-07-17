package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.ineg;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class inegReader implements Reader<ineg> {

    private final ineg instance = new ineg();

    @Override
    public ineg read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
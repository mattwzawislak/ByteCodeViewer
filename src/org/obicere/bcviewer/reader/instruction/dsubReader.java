package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.dsub;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class dsubReader implements Reader<dsub> {

    private final dsub instance = new dsub();

    @Override
    public dsub read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
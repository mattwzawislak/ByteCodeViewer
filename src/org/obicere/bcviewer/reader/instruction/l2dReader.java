package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.l2d;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class l2dReader implements Reader<l2d> {

    private final l2d instance = new l2d();

    @Override
    public l2d read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
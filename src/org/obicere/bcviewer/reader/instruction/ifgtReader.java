package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.ifgt;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class ifgtReader implements Reader<ifgt> {

    private final ifgt instance = new ifgt();

    @Override
    public ifgt read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
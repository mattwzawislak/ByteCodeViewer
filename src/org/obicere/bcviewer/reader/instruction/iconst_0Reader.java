package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.iconst_0;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class iconst_0Reader implements Reader<iconst_0> {

    private final iconst_0 instance = new iconst_0();

    @Override
    public iconst_0 read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
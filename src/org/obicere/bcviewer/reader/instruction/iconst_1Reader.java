package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.iconst_1;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class iconst_1Reader implements Reader<iconst_1> {

    @Override
    public iconst_1 read(final IndexedDataInputStream input) throws IOException {
        return new iconst_1();
    }
}
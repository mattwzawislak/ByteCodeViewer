package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.iconst_m1;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class iconst_m1Reader implements Reader<iconst_m1> {

    @Override
    public iconst_m1 read(final IndexedDataInputStream input) throws IOException {
        return new iconst_m1();
    }
}
package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.dload_2;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class dload_2Reader implements Reader<dload_2> {

    @Override
    public dload_2 read(final IndexedDataInputStream input) throws IOException {
        return new dload_2();
    }
}
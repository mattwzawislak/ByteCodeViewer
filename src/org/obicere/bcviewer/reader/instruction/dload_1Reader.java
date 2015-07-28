package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.dload_1;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class dload_1Reader implements Reader<dload_1> {

    @Override
    public dload_1 read(final IndexedDataInputStream input) throws IOException {
        return new dload_1();
    }
}
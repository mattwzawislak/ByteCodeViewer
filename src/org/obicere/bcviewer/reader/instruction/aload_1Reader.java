package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.aload_1;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class aload_1Reader implements Reader<aload_1> {

    @Override
    public aload_1 read(final IndexedDataInputStream input) throws IOException {
        return new aload_1();
    }
}
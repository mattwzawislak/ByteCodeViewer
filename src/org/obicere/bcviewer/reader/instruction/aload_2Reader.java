package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.aload_2;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class aload_2Reader implements Reader<aload_2> {

    @Override
    public aload_2 read(final IndexedDataInputStream input) throws IOException {
        return new aload_2();
    }
}
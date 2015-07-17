package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.if_icmpeq;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class if_icmpeqReader implements Reader<if_icmpeq> {

    private final if_icmpeq instance = new if_icmpeq();

    @Override
    public if_icmpeq read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
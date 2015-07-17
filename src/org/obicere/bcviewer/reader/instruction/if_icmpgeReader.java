package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.if_icmpge;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class if_icmpgeReader implements Reader<if_icmpge> {

    private final if_icmpge instance = new if_icmpge();

    @Override
    public if_icmpge read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.if_icmpne;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class if_icmpneReader implements Reader<if_icmpne> {

    private final if_icmpne instance = new if_icmpne();

    @Override
    public if_icmpne read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
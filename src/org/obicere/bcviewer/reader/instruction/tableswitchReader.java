package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.tableswitch;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class tableswitchReader implements Reader<tableswitch> {

    private final tableswitch instance = new tableswitch();

    @Override
    public tableswitch read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
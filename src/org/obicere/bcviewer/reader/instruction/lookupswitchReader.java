package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.lookupswitch;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class lookupswitchReader implements Reader<lookupswitch> {
    @Override
    public lookupswitch read(final IndexedDataInputStream input) throws IOException {
        return null;
    }
}
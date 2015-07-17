package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.invokeinterface;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class invokeinterfaceReader implements Reader<invokeinterface> {

    private final invokeinterface instance = new invokeinterface();

    @Override
    public invokeinterface read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
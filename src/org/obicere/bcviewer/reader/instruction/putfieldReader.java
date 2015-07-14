package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.putfield;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class putfieldReader implements Reader<putfield> {
    @Override
    public putfield read(final IndexedDataInputStream input) throws IOException {
        return null;
    }
}
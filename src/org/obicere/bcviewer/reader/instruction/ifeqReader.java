package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.ifeq;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class ifeqReader implements Reader<ifeq> {
    @Override
    public ifeq read(final IndexedDataInputStream input) throws IOException {
        return null;
    }
}
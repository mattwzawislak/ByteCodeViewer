package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.invokestatic;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class invokestaticReader implements Reader<invokestatic> {

    private final invokestatic instance = new invokestatic();

    @Override
    public invokestatic read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
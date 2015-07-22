package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.EmptyTarget;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class EmptyTargetReader implements Reader<EmptyTarget> {
    @Override
    public EmptyTarget read(final IndexedDataInputStream input) throws IOException {
        return new EmptyTarget(input.readUnsignedByte());
    }
}

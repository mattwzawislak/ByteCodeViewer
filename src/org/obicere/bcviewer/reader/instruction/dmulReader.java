package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.dmul;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class dmulReader implements Reader<dmul> {

    private final dmul instance = new dmul();

    @Override
    public dmul read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
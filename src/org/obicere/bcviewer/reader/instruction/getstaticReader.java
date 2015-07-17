package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.getstatic;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class getstaticReader implements Reader<getstatic> {

    private final getstatic instance = new getstatic();

    @Override
    public getstatic read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.goto_w;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class goto_wReader implements Reader<goto_w> {

    private final goto_w instance = new goto_w();

    @Override
    public goto_w read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.goto_;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class goto_Reader implements Reader<goto_> {

    private final goto_ instance = new goto_();

    @Override
    public goto_ read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
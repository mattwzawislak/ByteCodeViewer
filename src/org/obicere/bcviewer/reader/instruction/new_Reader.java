package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.new_;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class new_Reader implements Reader<new_> {

    private final new_ instance = new new_();

    @Override
    public new_ read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
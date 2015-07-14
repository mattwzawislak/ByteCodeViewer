package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.dcmpl;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class dcmplReader implements Reader<dcmpl> {

    private final dcmpl instance = new dcmpl();

    @Override
    public dcmpl read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
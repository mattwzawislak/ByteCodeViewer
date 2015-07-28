package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.lxor;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class lxorReader implements Reader<lxor> {

    @Override
    public lxor read(final IndexedDataInputStream input) throws IOException {
        return new lxor();
    }
}
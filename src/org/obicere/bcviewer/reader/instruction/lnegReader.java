package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.lneg;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class lnegReader implements Reader<lneg> {
    @Override
    public lneg read(final IndexedDataInputStream input) throws IOException {
        return null;
    }
}
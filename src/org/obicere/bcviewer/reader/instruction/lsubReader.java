package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.lsub;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class lsubReader implements Reader<lsub> {

    @Override
    public lsub read(final IndexedDataInputStream input) throws IOException {
        return new lsub();
    }
}
package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.idiv;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class idivReader implements Reader<idiv> {

    @Override
    public idiv read(final IndexedDataInputStream input) throws IOException {
        return new idiv();
    }
}
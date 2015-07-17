package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.fadd;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class faddReader implements Reader<fadd> {

    private final fadd instance = new fadd();

    @Override
    public fadd read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
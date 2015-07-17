package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.multianewarray;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class multianewarrayReader implements Reader<multianewarray> {

    private final multianewarray instance = new multianewarray();

    @Override
    public multianewarray read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.invokedynamic;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class invokedynamicReader implements Reader<invokedynamic> {

    private final invokedynamic instance = new invokedynamic();

    @Override
    public invokedynamic read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
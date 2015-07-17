package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.f2d;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class f2dReader implements Reader<f2d> {

    private final f2d instance = new f2d();

    @Override
    public f2d read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}
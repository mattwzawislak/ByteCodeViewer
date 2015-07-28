package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.fcmpl;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class fcmplReader implements Reader<fcmpl> {

    @Override
    public fcmpl read(final IndexedDataInputStream input) throws IOException {
        return new fcmpl();
    }
}
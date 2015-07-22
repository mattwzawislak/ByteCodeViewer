package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.LineNumber;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class LineNumberReader implements Reader<LineNumber> {
    @Override
    public LineNumber read(final IndexedDataInputStream input) throws IOException {
        final int startPC = input.readUnsignedShort();
        final int lineNumber = input.readUnsignedShort();
        return new LineNumber(startPC, lineNumber);
    }
}

package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.SourceFileAttribute;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class SourceFileAttributeReader implements Reader<SourceFileAttribute> {

    @Override
    public SourceFileAttribute read(final IndexedDataInputStream input) throws IOException {
        final int sourceFileIndex = input.readUnsignedShort();
        return new SourceFileAttribute(sourceFileIndex);
    }
}

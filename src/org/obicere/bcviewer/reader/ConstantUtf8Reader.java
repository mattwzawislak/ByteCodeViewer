package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.ConstantUtf8;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class ConstantUtf8Reader implements Reader<ConstantUtf8>{
    @Override
    public ConstantUtf8 read(final IndexedDataInputStream input) throws IOException {
        return new ConstantUtf8(input.readUTF());
    }
}

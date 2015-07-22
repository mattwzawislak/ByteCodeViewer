package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.CatchTarget;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class CatchTargetReader implements Reader<CatchTarget> {
    @Override
    public CatchTarget read(final IndexedDataInputStream input) throws IOException {
        final int targetType = input.readUnsignedByte();
        final int exceptionTableIndex = input.readUnsignedShort();
        return new CatchTarget(targetType, exceptionTableIndex);
    }
}

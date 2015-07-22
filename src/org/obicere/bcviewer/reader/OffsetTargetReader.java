package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.OffsetTarget;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class OffsetTargetReader implements Reader<OffsetTarget> {
    @Override
    public OffsetTarget read(final IndexedDataInputStream input) throws IOException {
        final int targetType = input.readUnsignedByte();
        final int offset = input.readUnsignedShort();
        return new OffsetTarget(targetType, offset);
    }
}

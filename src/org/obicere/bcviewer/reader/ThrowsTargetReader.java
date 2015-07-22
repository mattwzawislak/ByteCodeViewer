package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.ThrowsTarget;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class ThrowsTargetReader implements Reader<ThrowsTarget> {
    @Override
    public ThrowsTarget read(final IndexedDataInputStream input) throws IOException {
        final int targetType = input.readUnsignedByte();
        final int throwsTypeIndex = input.readUnsignedShort();
        return new ThrowsTarget(targetType, throwsTypeIndex);
    }
}

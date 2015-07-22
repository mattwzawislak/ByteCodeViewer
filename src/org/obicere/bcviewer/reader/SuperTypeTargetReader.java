package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.SuperTypeTarget;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class SuperTypeTargetReader implements Reader<SuperTypeTarget> {
    @Override
    public SuperTypeTarget read(final IndexedDataInputStream input) throws IOException {
        final int targetType = input.readUnsignedByte();
        final int superTypeTarget = input.readUnsignedShort();
        return new SuperTypeTarget(targetType, superTypeTarget);
    }
}

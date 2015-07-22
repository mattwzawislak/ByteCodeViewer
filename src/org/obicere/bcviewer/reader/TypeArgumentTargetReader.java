package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.TypeArgumentTarget;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class TypeArgumentTargetReader implements Reader<TypeArgumentTarget> {
    @Override
    public TypeArgumentTarget read(final IndexedDataInputStream input) throws IOException {
        final int targetType = input.readUnsignedByte();
        final int offset = input.readUnsignedShort();
        final int typeArgumentIndex = input.readUnsignedByte();
        return new TypeArgumentTarget(targetType, offset, typeArgumentIndex);
    }
}

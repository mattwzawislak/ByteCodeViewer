package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.TypeParameterBoundTarget;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class TypeParameterBoundTargetReader implements Reader<TypeParameterBoundTarget> {

    @Override
    public TypeParameterBoundTarget read(final IndexedDataInputStream input) throws IOException {
        final int targetType = input.readUnsignedByte();
        final int typeParameterIndex = input.readUnsignedByte();
        final int boundIndex = input.readUnsignedByte();
        return new TypeParameterBoundTarget(targetType, typeParameterIndex, boundIndex);
    }
}

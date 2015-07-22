package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.TypeParameterTarget;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class TypeParameterTargetReader implements Reader<TypeParameterTarget> {

    @Override
    public TypeParameterTarget read(final IndexedDataInputStream input) throws IOException {
        final int targetType = input.readUnsignedByte();
        final int typeParameterIndex = input.readUnsignedByte();
        return new TypeParameterTarget(targetType, typeParameterIndex);
    }
}

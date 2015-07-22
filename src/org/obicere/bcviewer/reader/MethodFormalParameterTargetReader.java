package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.MethodFormalParameterTarget;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class MethodFormalParameterTargetReader implements Reader<MethodFormalParameterTarget> {
    @Override
    public MethodFormalParameterTarget read(final IndexedDataInputStream input) throws IOException {
        final int targetType = input.readUnsignedByte();
        final int formalParameterIndex = input.readUnsignedByte();
        return new MethodFormalParameterTarget(targetType, formalParameterIndex);
    }
}

package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.Path;
import org.obicere.bcviewer.bytecode.TypePath;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class TypePathReader implements Reader<TypePath> {
    @Override
    public TypePath read(final IndexedDataInputStream input) throws IOException {
        final int pathLength = input.readUnsignedByte();
        final Path[] path = new Path[pathLength];
        for(int i = 0; i < pathLength; i++){
            final int typePathKind = input.readUnsignedByte();
            final int typeArgumentIndex = input.readUnsignedByte();
            path[i] = new Path(typePathKind, typeArgumentIndex);
        }
        return new TypePath(path);
    }
}

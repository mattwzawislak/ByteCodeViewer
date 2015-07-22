package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.LocalVar;
import org.obicere.bcviewer.bytecode.LocalVarTarget;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class LocalVarTargetReader implements Reader<LocalVarTarget> {

    private final LocalVarReader localVar = new LocalVarReader();

    @Override
    public LocalVarTarget read(final IndexedDataInputStream input) throws IOException {
        final int targetType = input.readUnsignedByte();
        final int tableLength = input.readUnsignedShort();
        final LocalVar[] table = new LocalVar[tableLength];
        for(int i = 0; i < tableLength; i++){
            table[i] = localVar.read(input);
        }
        return new LocalVarTarget(targetType, table);
    }
}

package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.LocalVariableType;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class LocalVariableTypeReader implements Reader<LocalVariableType> {
    @Override
    public LocalVariableType read(final IndexedDataInputStream input) throws IOException {
        final int startPC = input.readUnsignedShort();
        final int length = input.readUnsignedShort();
        final int nameIndex = input.readUnsignedShort();
        final int signatureIndex = input.readUnsignedShort();
        final int index = input.readUnsignedShort();
        return new LocalVariableType(startPC, length, nameIndex, signatureIndex, index);
    }
}

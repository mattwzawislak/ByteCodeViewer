package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.LocalVariableType;
import org.obicere.bcviewer.bytecode.LocalVariableTypeTableAttribute;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class LocalVariableTypeTableAttributeReader implements Reader<LocalVariableTypeTableAttribute> {

    private final LocalVariableTypeReader localVariableType = new LocalVariableTypeReader();

    @Override
    public LocalVariableTypeTableAttribute read(final IndexedDataInputStream input) throws IOException {
        final int localVariableTypeTableLength = input.readUnsignedShort();
        final LocalVariableType[] localVariableTypeTable = new LocalVariableType[localVariableTypeTableLength];

        for (int i = 0; i < localVariableTypeTableLength; i++) {
            localVariableTypeTable[i] = localVariableType.read(input);
        }
        return new LocalVariableTypeTableAttribute(localVariableTypeTable);
    }
}

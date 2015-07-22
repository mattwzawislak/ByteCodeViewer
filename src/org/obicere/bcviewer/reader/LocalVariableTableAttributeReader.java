package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.LocalVariable;
import org.obicere.bcviewer.bytecode.LocalVariableTableAttribute;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class LocalVariableTableAttributeReader implements Reader<LocalVariableTableAttribute> {

    private final LocalVariableReader localVariable = new LocalVariableReader();

    @Override
    public LocalVariableTableAttribute read(final IndexedDataInputStream input) throws IOException {
        final int localVariableTableLength = input.readUnsignedShort();
        final LocalVariable[] localVariableTable = new LocalVariable[localVariableTableLength];

        for (int i = 0; i < localVariableTableLength; i++) {
            localVariableTable[i] = localVariable.read(input);
        }
        return new LocalVariableTableAttribute(localVariableTable);
    }
}

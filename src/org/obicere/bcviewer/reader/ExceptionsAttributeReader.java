package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.ExceptionsAttribute;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class ExceptionsAttributeReader implements Reader<ExceptionsAttribute> {
    @Override
    public ExceptionsAttribute read(final IndexedDataInputStream input) throws IOException {
        final int numberOfExceptions = input.readUnsignedShort();
        final int[] exceptionIndexTable = new int[numberOfExceptions];
        for(int i = 0; i < numberOfExceptions; i++){
            exceptionIndexTable[i] = input.readUnsignedShort();
        }
        return new ExceptionsAttribute(exceptionIndexTable);
    }
}

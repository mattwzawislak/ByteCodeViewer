package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.ArrayElementValue;
import org.obicere.bcviewer.bytecode.ElementValue;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class ArrayElementValueReader implements Reader<ArrayElementValue> {

    private final ElementValueReader elementValue;

    public ArrayElementValueReader(final ElementValueReader elementValue) {
        this.elementValue = elementValue;
    }

    @Override
    public ArrayElementValue read(final IndexedDataInputStream input) throws IOException {
        final int numValues = input.readUnsignedShort();
        final ElementValue[] values = new ElementValue[numValues];

        for (int i = 0; i < numValues; i++) {
            values[i] = elementValue.read(input);
        }
        return new ArrayElementValue(values);
    }
}

package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.Parameter;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class ParameterReader implements Reader<Parameter> {
    @Override
    public Parameter read(final IndexedDataInputStream input) throws IOException {
        final int nameIndex = input.readUnsignedShort();
        final int accessFlags = input.readUnsignedShort();
        return new Parameter(nameIndex, accessFlags);
    }
}

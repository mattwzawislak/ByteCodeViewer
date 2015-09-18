package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.ConstantInterfaceMethodRef;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class ConstantInterfaceMethodRefReader implements Reader<ConstantInterfaceMethodRef> {
    @Override
    public ConstantInterfaceMethodRef read(final IndexedDataInputStream input) throws IOException {
        return new ConstantInterfaceMethodRef(input.readUnsignedShort(), input.readUnsignedShort());
    }
}

package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.ConstantInterfaceMethodRef_;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class ConstantInterfaceMethodRefReader implements Reader<ConstantInterfaceMethodRef_> {
    @Override
    public ConstantInterfaceMethodRef_ read(final IndexedDataInputStream input) throws IOException {
        return new ConstantInterfaceMethodRef_(input.readUnsignedShort(), input.readUnsignedShort());
    }
}

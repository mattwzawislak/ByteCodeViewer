package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.ConstantInvokeDynamic;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class ConstantInvokeDynamicReader implements Reader<ConstantInvokeDynamic> {
    @Override
    public ConstantInvokeDynamic read(final IndexedDataInputStream input) throws IOException {
        return new ConstantInvokeDynamic(input.readUnsignedShort(), input.readUnsignedShort());
    }
}

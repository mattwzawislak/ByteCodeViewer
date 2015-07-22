package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.InnerClass;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class InnerClassReader implements Reader<InnerClass> {
    @Override
    public InnerClass read(final IndexedDataInputStream input) throws IOException {
        final int innerClassInfoIndex = input.readUnsignedShort();
        final int outerClassInfoIndex = input.readUnsignedShort();
        final int innerNameIndex = input.readUnsignedShort();
        final int innerClassAccessFlags = input.readUnsignedShort();
        return new InnerClass(innerClassInfoIndex, outerClassInfoIndex, innerNameIndex, innerClassAccessFlags);
    }
}

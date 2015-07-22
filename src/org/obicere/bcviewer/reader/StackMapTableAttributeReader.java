package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.StackMapFrame;
import org.obicere.bcviewer.bytecode.StackMapTableAttribute;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class StackMapTableAttributeReader implements Reader<StackMapTableAttribute> {

    private final StackMapFrameReader stackMapFrame = new StackMapFrameReader();

    @Override
    public StackMapTableAttribute read(final IndexedDataInputStream input) throws IOException {
        final int numberOfEntries = input.readUnsignedShort();
        final StackMapFrame[] entries = new StackMapFrame[numberOfEntries];
        for (int i = 0; i < numberOfEntries; i++) {
            entries[i] = stackMapFrame.read(input);
        }
        return new StackMapTableAttribute(entries);
    }
}

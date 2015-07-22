package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.SourceDebugExtensionAttribute;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class SourceDebugExtensionAttributeReader implements Reader<SourceDebugExtensionAttribute> {
    @Override
    public SourceDebugExtensionAttribute read(final IndexedDataInputStream input) throws IOException {
        input.stepBack(4);
        final int attributeLength = input.readInt();
        final byte[] debugExtension = new byte[attributeLength];
        if (input.read(debugExtension) < 0) {
            throw new ClassFormatError("reached out of file when reading source debug extension");
        }
        return new SourceDebugExtensionAttribute(new String(debugExtension));
    }
}

package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.CharacterElementValue;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class CharacterElementValueReader implements Reader<CharacterElementValue> {
    @Override
    public CharacterElementValue read(final IndexedDataInputStream input) throws IOException {
        return new CharacterElementValue(input.readUnsignedShort());
    }
}

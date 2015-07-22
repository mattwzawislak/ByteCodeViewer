package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.SignatureAttribute;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class SignatureAttributeReader implements Reader<SignatureAttribute> {
    @Override
    public SignatureAttribute read(final IndexedDataInputStream input) throws IOException {
        final int signatureIndex = input.readUnsignedShort();
        return new SignatureAttribute(signatureIndex);
    }
}

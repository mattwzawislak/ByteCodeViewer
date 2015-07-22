package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.Attribute;
import org.obicere.bcviewer.bytecode.Method;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class MethodReader implements Reader<Method> {

    private final AttributeReader attributeReader;

    public MethodReader(final AttributeReader attributeReader) {
        this.attributeReader = attributeReader;
    }

    @Override
    public Method read(final IndexedDataInputStream input) throws IOException {
        final int accessFlags = input.readUnsignedShort();
        final int nameIndex = input.readUnsignedShort();
        final int descriptorIndex = input.readUnsignedShort();

        final int attributesCount = input.readUnsignedShort();
        final Attribute[] attributes = new Attribute[attributesCount];
        for(int i = 0; i < attributesCount; i++){
            attributes[i] = attributeReader.read(input);
        }
        return new Method(accessFlags, nameIndex, descriptorIndex, attributes);
    }
}

package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.Attribute;
import org.obicere.bcviewer.bytecode.CodeAttribute;
import org.obicere.bcviewer.bytecode.CodeException;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class CodeAttributeReader implements Reader<CodeAttribute> {

    private final AttributeReader attributeReader;

    public CodeAttributeReader(final AttributeReader attributeReader) {
        this.attributeReader = attributeReader;
    }

    @Override
    public CodeAttribute read(final IndexedDataInputStream input) throws IOException {
        final int maxStack = input.readUnsignedShort();
        final int maxLocals = input.readUnsignedShort();
        final int codeLength = input.readInt();

        final byte[] code = new byte[codeLength];
        if(input.read(code) < 0){
            throw new ClassFormatError("reached end of file while reading instructions.");
        }

        final int exceptionTableLength = input.readUnsignedShort();
        final CodeException[] exceptionTable = new CodeException[exceptionTableLength];

        for(int i = 0; i < exceptionTableLength; i++){
            final int startPC = input.readUnsignedShort();
            final int endPC = input.readUnsignedShort();
            final int handlerPC = input.readUnsignedShort();
            final int catchType = input.readUnsignedShort();
            exceptionTable[i] = new CodeException(startPC, endPC, handlerPC, catchType);
        }
        final int attributesCount = input.readUnsignedShort();
        final Attribute[] attributes = new Attribute[attributesCount];
        for(int i = 0; i < attributesCount; i++){
            attributes[i] = attributeReader.read(input);
        }
        return new CodeAttribute(maxStack, maxLocals, code, exceptionTable, attributes);
    }
}

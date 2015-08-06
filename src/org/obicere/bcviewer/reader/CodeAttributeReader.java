package org.obicere.bcviewer.reader;

import org.obicere.bcviewer.bytecode.Attribute;
import org.obicere.bcviewer.bytecode.CodeAttribute;
import org.obicere.bcviewer.bytecode.CodeException;
import org.obicere.bcviewer.bytecode.instruction.Instruction;
import org.obicere.bcviewer.reader.instruction.InstructionReader;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Obicere
 */
public class CodeAttributeReader implements Reader<CodeAttribute> {

    private final AttributeReader attributeReader;
    private final InstructionReader instructionReader = new InstructionReader();

    public CodeAttributeReader(final AttributeReader attributeReader) {
        this.attributeReader = attributeReader;
    }

    @Override
    public CodeAttribute read(final IndexedDataInputStream input) throws IOException {
        final int maxStack = input.readUnsignedShort();
        final int maxLocals = input.readUnsignedShort();
        final int codeLength = input.readInt();

        final int codeOffset = input.getOffsetIndex();

        final byte[] code = new byte[codeLength];
        if (input.read(code) < 0) {
            throw new ClassFormatError("reached end of file while reading instructions.");
        }

        final IndexedDataInputStream codeReader = new IndexedDataInputStream(codeOffset, code);

        // set the default size to the code length. Every instruction takes
        // at least 1 byte, meaning there is at a maximum of _codeLength_
        // instructions to be read
        final ArrayList<Instruction> instructionList = new ArrayList<>(codeLength);

        while (codeReader.available() > 0) { // While there are remaining instructions
            instructionList.add(instructionReader.read(codeReader));
        }
        final Instruction[] instructions = instructionList.toArray(new Instruction[instructionList.size()]);

        final int exceptionTableLength = input.readUnsignedShort();
        final CodeException[] exceptionTable = new CodeException[exceptionTableLength];

        for (int i = 0; i < exceptionTableLength; i++) {
            final int startIndex = input.getOffsetIndex();
            final int startPC = input.readUnsignedShort();
            final int endPC = input.readUnsignedShort();
            final int handlerPC = input.readUnsignedShort();
            final int catchType = input.readUnsignedShort();
            final CodeException exception = new CodeException(startPC, endPC, handlerPC, catchType);
            final int endIndex = input.getOffsetIndex();

            exception.setBounds(startIndex, endIndex);
            exceptionTable[i] = exception;
        }
        final int attributesCount = input.readUnsignedShort();
        final Attribute[] attributes = new Attribute[attributesCount];
        for (int i = 0; i < attributesCount; i++) {
            attributes[i] = attributeReader.read(input);
        }
        return new CodeAttribute(maxStack, maxLocals, code, instructions, exceptionTable, attributes);
    }
}

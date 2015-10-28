package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.wide;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class wideReader implements Reader<wide> {

    @Override
    public wide read(final IndexedDataInputStream input) throws IOException {
        final int opcode = input.readUnsignedByte();
        if (!isValidWideTag(opcode)) {
            throw new ClassFormatError("invalid operation after a wide instruction listing: " + opcode);
        }
        if (opcode == InstructionReader.OPCODE_IINC) { // need to read two extra bytes
            return new wide(opcode, input.readUnsignedByte(), input.readUnsignedByte(), input.readUnsignedByte(), input.readUnsignedByte());
        } else {
            return new wide(opcode, input.readUnsignedByte(), input.readUnsignedByte());
        }
    }

    private boolean isValidWideTag(final int opcode) {
        switch (opcode) {
            case InstructionReader.OPCODE_IINC:
            case InstructionReader.OPCODE_ILOAD:
            case InstructionReader.OPCODE_FLOAD:
            case InstructionReader.OPCODE_LLOAD:
            case InstructionReader.OPCODE_DLOAD:
            case InstructionReader.OPCODE_ISTORE:
            case InstructionReader.OPCODE_ASTORE:
            case InstructionReader.OPCODE_LSTORE:
            case InstructionReader.OPCODE_DSTORE:
            case InstructionReader.OPCODE_RET:
                return true;
            default:
                return false;
        }
    }
}
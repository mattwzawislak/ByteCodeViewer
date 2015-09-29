package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.Instruction;
import org.obicere.bcviewer.bytecode.instruction.wide;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class wideReader implements Reader<wide> {

    private final InstructionReader instructionReader;

    public wideReader(final InstructionReader instructionReader) {
        this.instructionReader = instructionReader;
    }

    @Override
    public wide read(final IndexedDataInputStream input) throws IOException {
        final int opcode = input.readUnsignedByte();
        if (opcode == InstructionReader.OPCODE_IINC) { // iinc opcode
            return new wide(opcode, input.readUnsignedByte(), input.readUnsignedByte(), input.readUnsignedByte(), input.readUnsignedByte());
        } else if (opcode == InstructionReader.OPCODE_ILOAD || opcode == InstructionReader.OPCODE_FLOAD || opcode == InstructionReader.OPCODE_LLOAD ||
                   opcode == InstructionReader.OPCODE_DLOAD || opcode == InstructionReader.OPCODE_ISTORE || opcode == InstructionReader.OPCODE_ASTORE ||
                   opcode == InstructionReader.OPCODE_LSTORE || opcode == InstructionReader.OPCODE_DSTORE || opcode == InstructionReader.OPCODE_RET) {
            // Check to ensure this is a valid wide instruction for a wide instruction
            return new wide(opcode, input.readUnsignedByte(), input.readUnsignedByte());
        } else {
            throw new ClassFormatError("invalid operation after a wide instruction listing: " + opcode);
        }
    }
}
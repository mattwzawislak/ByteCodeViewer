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

    private final Object instructionReader;

    public wideReader(final Object instructionReader) {
        this.instructionReader = instructionReader;
    }

    @Override
    public wide read(final IndexedDataInputStream input) throws IOException {
        final Instruction contain = null; // instructionReader.readInstruction(input.nextUnsignedByte());
        if (contain.getOpcode() == 0x84) { // iinc opcode
            return new wide(contain, input.readUnsignedByte(), input.readUnsignedByte(), input.readUnsignedByte(), input.readUnsignedByte());
        } else {
            // Check to ensure this is a valid wide instruction
            return new wide(contain, input.readUnsignedByte(), input.readUnsignedByte());
        }
    }
}
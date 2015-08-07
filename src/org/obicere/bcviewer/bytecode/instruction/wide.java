package org.obicere.bcviewer.bytecode.instruction;

import org.obicere.bcviewer.bytecode.ConstantPool;
import org.obicere.bcviewer.reader.instruction.InstructionReader;

/**
 * @author Obicere
 */
public class wide extends Instruction {

    private static final String MNEMONIC = "wide";
    private static final int    OPCODE   = 0xc4;

    private final Instruction instruction;

    private final int indexbyte1;
    private final int indexbyte2;

    private final int constbyte1;
    private final int constbyte2;

    public wide(final Instruction instruction, final int indexbyte1, final int indexbyte2) {
        super(MNEMONIC, OPCODE);
        this.instruction = instruction;
        this.indexbyte1 = indexbyte1;
        this.indexbyte2 = indexbyte2;
        this.constbyte1 = 0;
        this.constbyte2 = 0;
    }

    public wide(final Instruction instruction, final int indexbyte1, final int indexbyte2, final int constbyte1, final int constbyte2) {
        super(MNEMONIC, OPCODE);
        this.instruction = instruction;
        this.indexbyte1 = indexbyte1;
        this.indexbyte2 = indexbyte2;
        this.constbyte1 = constbyte1;
        this.constbyte2 = constbyte2;
    }

    public int getIndexbyte1() {
        return indexbyte1;
    }

    public int getIndexbyte2() {
        return indexbyte2;
    }

    public int getIndex() {
        return (indexbyte1 << 8) | indexbyte2;
    }

    public int getConstbyte1() {
        return constbyte1;
    }

    public int getConstbyte2() {
        return constbyte2;
    }

    public int getConst() {
        return (constbyte1 << 8) | constbyte2;
    }

    public Instruction getInstruction() {
        return instruction;
    }

    @Override
    public String toString(final ConstantPool constantPool) {
        final StringBuilder builder = new StringBuilder(MNEMONIC);
        builder.append(' ');
        builder.append(instruction.toString(constantPool));
        builder.append(' ');
        builder.append(getIndex());
        if (instruction.getOpcode() == InstructionReader.OPCODE_IINC) {
            builder.append(' ');
            builder.append(getConst());
        }
        return builder.toString();
    }
}

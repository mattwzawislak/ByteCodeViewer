package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class invokeinterface extends Instruction {

    private static final String MNEMONIC = "invokeinterface";
    private static final int    OPCODE   = 0xb9;

    private final int indexbyte1;
    private final int indexbyte2;
    private final int count;

    public invokeinterface(final int indexbyte1, final int indexbyte2, final int count, final int indexbyte4) {
        super(MNEMONIC, OPCODE);
        if (count == 0) {
            throw new IllegalArgumentException("count operand must not be 0.");
        }
        if (indexbyte4 != 0) {
            throw new IllegalArgumentException("invokeinerface byte 4 must be set to 0.");
        }
        this.indexbyte1 = indexbyte1;
        this.indexbyte2 = indexbyte2;
        this.count = count;
    }

    public int getIndexbyte1() {
        return indexbyte1;
    }

    public int getIndexbyte2() {
        return indexbyte2;
    }

    public int getCount() {
        return count;
    }

    public int getIndex() {
        return (indexbyte1 << 8) | indexbyte2;
    }
}

package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class checkcast extends Instruction {

    private static final String MNEMONIC = "checkcast";
    private static final int    OPCODE   = 0xc0;

    private final int indexbyte1;
    private final int indexbyte2;

    public checkcast(final int indexbyte1, final int indexbyte2) {
        super(MNEMONIC, OPCODE);
        this.indexbyte1 = indexbyte1;
        this.indexbyte2 = indexbyte2;
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

}

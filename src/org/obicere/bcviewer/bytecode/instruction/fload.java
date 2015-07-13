package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class fload extends Instruction {

    private static final String MNEMONIC = "fload";
    private static final int    OPCODE   = 0x17;

    private final int index;

    public fload(final int index) {
        super(MNEMONIC, OPCODE);
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}

package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class fstore extends Instruction {

    private static final String MNEMONIC = "fstore";
    private static final int    OPCODE   = 0x38;

    private final int index;

    public fstore(final int index) {
        super(MNEMONIC, OPCODE);
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}

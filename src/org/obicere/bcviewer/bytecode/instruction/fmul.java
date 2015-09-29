package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class fmul extends Instruction {

    private static final String MNEMONIC = "fmul";
    private static final int    OPCODE   = 0x6a;

    public fmul() {
        super(MNEMONIC, OPCODE);
    }
}

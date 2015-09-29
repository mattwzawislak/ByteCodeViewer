package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class fconst_1 extends Instruction {

    private static final String MNEMONIC = "fconst_1";
    private static final int    OPCODE   = 0x0c;

    public fconst_1() {
        super(MNEMONIC, OPCODE);
    }
}

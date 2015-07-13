package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class fconst_2 extends Instruction {

    private static final String MNEMONIC = "fconst_2";
    private static final int    OPCODE   = 0x0d;

    public fconst_2() {
        super(MNEMONIC, OPCODE);
    }
}

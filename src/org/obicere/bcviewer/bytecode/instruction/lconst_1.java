package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class lconst_1 extends Instruction {

    private static final String MNEMONIC = "lconst_1";
    private static final int    OPCODE   = 0x0a;

    public lconst_1() {
        super(MNEMONIC, OPCODE);
    }
}

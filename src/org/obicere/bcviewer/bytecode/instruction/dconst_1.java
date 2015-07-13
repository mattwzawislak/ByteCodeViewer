package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class dconst_1 extends Instruction {

    private static final String MNEMONIC = "dconst_1";
    private static final int    OPCODE   = 0x0f;

    public dconst_1() {
        super(MNEMONIC, OPCODE);
    }

}

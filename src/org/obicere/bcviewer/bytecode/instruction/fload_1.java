package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class fload_1 extends Instruction {

    private static final String MNEMONIC = "fload_1";
    private static final int    OPCODE   = 0x23;

    public fload_1() {
        super(MNEMONIC, OPCODE);
    }
}

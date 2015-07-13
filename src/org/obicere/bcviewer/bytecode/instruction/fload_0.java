package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class fload_0 extends Instruction {

    private static final String MNEMONIC = "fload_0";
    private static final int    OPCODE   = 0x22;

    public fload_0() {
        super(MNEMONIC, OPCODE);
    }
}

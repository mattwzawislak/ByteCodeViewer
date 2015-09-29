package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class dup2_x2 extends Instruction {

    private static final String MNEMONIC = "dup2_x2";
    private static final int    OPCODE   = 0x5e;

    public dup2_x2() {
        super(MNEMONIC, OPCODE);
    }
}

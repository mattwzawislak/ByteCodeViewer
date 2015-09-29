package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class nop extends Instruction {

    private static final String MNEMONIC = "nop";
    private static final int    OPCODE   = 0x00;

    public nop() {
        super(MNEMONIC, OPCODE);
    }
}

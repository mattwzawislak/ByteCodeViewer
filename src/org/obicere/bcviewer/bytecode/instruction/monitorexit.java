package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class monitorexit extends Instruction {

    private static final String MNEMONIC = "monitorexit";
    private static final int    OPCODE   = 0xc3;

    public monitorexit() {
        super(MNEMONIC, OPCODE);
    }
}

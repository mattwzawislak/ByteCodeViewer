package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class swap extends Instruction {

    private static final String MNEMONIC = "swap";
    private static final int    OPCODE   = 0x5f;

    public swap() {
        super(MNEMONIC, OPCODE);
    }
}

package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class iaload extends Instruction {

    private static final String MNEMONIC = "iaload";
    private static final int    OPCODE   = 0x2e;

    public iaload() {
        super(MNEMONIC, OPCODE);
    }
}

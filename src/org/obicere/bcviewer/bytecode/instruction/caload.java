package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class caload extends Instruction {

    private static final String MNEMONIC = "caload";
    private static final int    OPCODE   = 0x34;

    public caload() {
        super(MNEMONIC, OPCODE);
    }
}

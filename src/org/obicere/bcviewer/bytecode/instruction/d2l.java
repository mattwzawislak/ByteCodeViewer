package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class d2l extends Instruction {

    private static final String MNEMONIC = "d2l";
    private static final int    OPCODE   = 0x8f;

    public d2l() {
        super(MNEMONIC, OPCODE);
    }
}

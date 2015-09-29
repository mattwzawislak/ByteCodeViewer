package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class l2d extends Instruction {

    private static final String MNEMONIC = "l2d";
    private static final int    OPCODE   = 0x8a;

    public l2d() {
        super(MNEMONIC, OPCODE);
    }
}

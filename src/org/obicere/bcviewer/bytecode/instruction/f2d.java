package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class f2d extends Instruction {

    private static final String MNEMONIC = "f2d";
    private static final int    OPCODE   = 0x8d;

    public f2d() {
        super(MNEMONIC, OPCODE);
    }
}

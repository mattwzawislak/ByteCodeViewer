package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class i2d extends Instruction {

    private static final String MNEMONIC = "i2d";
    private static final int    OPCODE   = 0x87;

    public i2d() {
        super(MNEMONIC, OPCODE);
    }
}

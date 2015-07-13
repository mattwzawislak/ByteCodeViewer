package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class i2c extends Instruction {

    private static final String MNEMONIC = "i2c";
    private static final int    OPCODE   = 0x92;

    public i2c() {
        super(MNEMONIC, OPCODE);
    }
}

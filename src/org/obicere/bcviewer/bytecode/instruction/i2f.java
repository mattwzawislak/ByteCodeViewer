package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class i2f extends Instruction {

    private static final String MNEMONIC = "i2f";
    private static final int    OPCODE   = 0x86;

    public i2f() {
        super(MNEMONIC, OPCODE);
    }
}

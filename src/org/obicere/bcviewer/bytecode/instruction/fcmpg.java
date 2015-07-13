package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class fcmpg extends Instruction {

    private static final String MNEMONIC = "fcmpg";
    private static final int    OPCODE   = 0x96;

    public fcmpg() {
        super(MNEMONIC, OPCODE);
    }
}

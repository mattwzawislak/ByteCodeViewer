package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class dcmpg extends Instruction {

    private static final String MNEMONIC = "dcmpg";
    private static final int    OPCODE   = 0x98;

    public dcmpg() {
        super(MNEMONIC, OPCODE);
    }
}

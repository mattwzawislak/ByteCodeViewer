package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class fdiv extends Instruction {

    private static final String MNEMONIC = "fdiv";
    private static final int    OPCODE   = 0x6e;

    public fdiv() {
        super(MNEMONIC, OPCODE);
    }
}

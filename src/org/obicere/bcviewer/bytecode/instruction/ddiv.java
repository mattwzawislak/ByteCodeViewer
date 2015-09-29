package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class ddiv extends Instruction {

    private static final String MNEMONIC = "ddiv";
    private static final int    OPCODE   = 0x6f;

    public ddiv() {
        super(MNEMONIC, OPCODE);
    }
}

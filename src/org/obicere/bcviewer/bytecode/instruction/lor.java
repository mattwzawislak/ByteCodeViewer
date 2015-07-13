package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class lor extends Instruction {

    private static final String MNEMONIC = "lor";
    private static final int    OPCODE   = 0x81;

    public lor() {
        super(MNEMONIC, OPCODE);
    }
}

package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class iadd extends Instruction {

    private static final String MNEMONIC = "iadd";
    private static final int    OPCODE   = 0x60;

    public iadd() {
        super(MNEMONIC, OPCODE);
    }
}

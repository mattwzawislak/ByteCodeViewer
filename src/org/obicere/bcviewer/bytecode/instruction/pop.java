package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class pop extends Instruction {

    private static final String MNEMONIC = "pop";
    private static final int    OPCODE   = 0x57;

    public pop() {
        super(MNEMONIC, OPCODE);
    }
}

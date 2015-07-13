package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class dup2 extends Instruction {

    private static final String MNEMONIC = "dup2";
    private static final int    OPCODE   = 0x5c;

    public dup2() {
        super(MNEMONIC, OPCODE);
    }
}

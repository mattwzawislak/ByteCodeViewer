package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class pop2 extends Instruction {

    private static final String MNEMONIC = "pop2";
    private static final int    OPCODE   = 0x58;

    public pop2() {
        super(MNEMONIC, OPCODE);
    }
}

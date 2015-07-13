package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class dneg extends Instruction {

    private static final String MNEMONIC = "dneg";
    private static final int    OPCODE   = 0x77;

    public dneg() {
        super(MNEMONIC, OPCODE);
    }

}

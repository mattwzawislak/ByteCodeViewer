package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class l2f extends Instruction {

    private static final String MNEMONIC = "l2f";
    private static final int    OPCODE   = 0x89;

    public l2f() {
        super(MNEMONIC, OPCODE);
    }
}

package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class athrow extends Instruction {

    private static final String MNEMONIC = "athrow";
    private static final int    OPCODE   = 0xbf;

    public athrow() {
        super(MNEMONIC, OPCODE);
    }
}

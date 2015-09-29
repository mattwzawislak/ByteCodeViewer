package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class dup_x1 extends Instruction {

    private static final String MNEMONIC = "dup_x1";
    private static final int    OPCODE   = 0x5a;

    public dup_x1() {
        super(MNEMONIC, OPCODE);
    }
}

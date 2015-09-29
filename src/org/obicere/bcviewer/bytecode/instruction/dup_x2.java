package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class dup_x2 extends Instruction {

    private static final String MNEMONIC = "dup_x2";
    private static final int    OPCODE   = 0x5b;

    public dup_x2() {
        super(MNEMONIC, OPCODE);
    }
}

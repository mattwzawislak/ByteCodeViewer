package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class lload_1 extends Instruction {

    private static final String MNEMONIC = "lload_1";
    private static final int    OPCODE   = 0x1f;

    public lload_1() {
        super(MNEMONIC, OPCODE);
    }
}

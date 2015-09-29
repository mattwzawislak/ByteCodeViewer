package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class lload_2 extends Instruction {

    private static final String MNEMONIC = "lload_2";
    private static final int    OPCODE   = 0x20;

    public lload_2() {
        super(MNEMONIC, OPCODE);
    }
}

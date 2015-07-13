package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class lload_0 extends Instruction {

    private static final String MNEMONIC = "lload_0";
    private static final int    OPCODE   = 0x1e;

    public lload_0() {
        super(MNEMONIC, OPCODE);
    }
}

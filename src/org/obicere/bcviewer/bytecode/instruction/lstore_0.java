package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class lstore_0 extends Instruction {

    private static final String MNEMONIC = "lstore_0";
    private static final int    OPCODE   = 0x3f;

    public lstore_0() {
        super(MNEMONIC, OPCODE);
    }
}

package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class lstore_1 extends Instruction {

    private static final String MNEMONIC = "lstore_1";
    private static final int    OPCODE   = 0x40;

    public lstore_1() {
        super(MNEMONIC, OPCODE);
    }
}

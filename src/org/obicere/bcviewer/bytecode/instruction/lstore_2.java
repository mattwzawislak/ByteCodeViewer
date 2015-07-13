package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class lstore_2 extends Instruction {

    private static final String MNEMONIC = "lstore";
    private static final int    OPCODE   = 0x41;

    public lstore_2() {
        super(MNEMONIC, OPCODE);
    }
}

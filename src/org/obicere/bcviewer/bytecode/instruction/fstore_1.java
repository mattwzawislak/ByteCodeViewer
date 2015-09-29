package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class fstore_1 extends Instruction {

    private static final String MNEMONIC = "fstore_1";
    private static final int    OPCODE   = 0x44;

    public fstore_1() {
        super(MNEMONIC, OPCODE);
    }
}

package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class fstore_0 extends Instruction {

    private static final String MNEMONIC = "fstore_0";
    private static final int    OPCODE   = 0x43;

    public fstore_0() {
        super(MNEMONIC, OPCODE);
    }
}

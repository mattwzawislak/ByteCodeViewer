package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class fstore_3 extends Instruction {

    private static final String MNEMONIC = "fstore_3";
    private static final int    OPCODE   = 0x46;

    public fstore_3() {
        super(MNEMONIC, OPCODE);
    }
}

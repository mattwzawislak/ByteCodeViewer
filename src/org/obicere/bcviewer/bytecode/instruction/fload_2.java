package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class fload_2 extends Instruction {

    private static final String MNEMONIC = "fload_2";
    private static final int    OPCODE   = 0x24;

    public fload_2() {
        super(MNEMONIC, OPCODE);
    }
}

package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class dconst_0 extends Instruction {

    private static final String MNEMONIC = "dconst_0";
    private static final int    OPCODE   = 0x0e;

    public dconst_0() {
        super(MNEMONIC, OPCODE);
    }

}

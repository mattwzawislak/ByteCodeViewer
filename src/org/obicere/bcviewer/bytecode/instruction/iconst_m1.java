package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class iconst_m1 extends Instruction {

    private static final String MNEMONIC = "iconst_m1";
    private static final int    OPCODE   = 0x02;

    public iconst_m1() {
        super(MNEMONIC, OPCODE);
    }
}

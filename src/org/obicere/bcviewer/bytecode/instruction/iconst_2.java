package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class iconst_2 extends Instruction {

    private static final String MNEMONIC = "iconst_2";
    private static final int    OPCODE   = 0x05;

    public iconst_2() {
        super(MNEMONIC, OPCODE);
    }
}

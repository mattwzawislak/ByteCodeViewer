package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class iconst_0 extends Instruction {

    private static final String MNEMONIC = "iconst_0";
    private static final int    OPCODE   = 0x03;

    public iconst_0() {
        super(MNEMONIC, OPCODE);
    }
}

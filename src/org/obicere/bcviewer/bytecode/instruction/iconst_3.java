package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class iconst_3 extends Instruction {

    private static final String MNEMONIC = "iconst_3";
    private static final int    OPCODE   = 0x06;

    public iconst_3() {
        super(MNEMONIC, OPCODE);
    }
}

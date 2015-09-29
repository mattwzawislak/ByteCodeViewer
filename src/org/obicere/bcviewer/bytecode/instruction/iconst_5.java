package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class iconst_5 extends Instruction {

    private static final String MNEMONIC = "iconst_5";
    private static final int    OPCODE   = 0x08;

    public iconst_5() {
        super(MNEMONIC, OPCODE);
    }
}

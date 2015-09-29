package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class iconst_4 extends Instruction {

    private static final String MNEMONIC = "iconst_4";
    private static final int    OPCODE   = 0x07;

    public iconst_4() {
        super(MNEMONIC, OPCODE);
    }
}

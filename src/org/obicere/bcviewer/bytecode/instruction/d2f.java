package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class d2f extends Instruction {

    private static final String MNEMONIC = "d2f";
    private static final int    OPCODE   = 0x90;

    public d2f() {
        super(MNEMONIC, OPCODE);
    }

}

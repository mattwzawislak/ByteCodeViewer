package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class l2i extends Instruction {

    private static final String MNEMONIC = "l2i";
    private static final int    OPCODE   = 0x88;

    public l2i() {
        super(MNEMONIC, OPCODE);
    }
}

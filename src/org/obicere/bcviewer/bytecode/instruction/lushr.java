package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class lushr extends Instruction {

    private static final String MNEMONIC = "lushr";
    private static final int    OPCODE   = 0x7d;

    public lushr() {
        super(MNEMONIC, OPCODE);
    }
}

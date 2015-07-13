package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class freturn extends Instruction {

    private static final String MNEMONIC = "freturn";
    private static final int    OPCODE   = 0xae;

    public freturn() {
        super(MNEMONIC, OPCODE);
    }
}

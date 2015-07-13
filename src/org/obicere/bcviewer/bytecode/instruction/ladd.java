package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class ladd extends Instruction {

    private static final String MNEMONIC = "ladd";
    private static final int    OPCODE   = 0x61;

    public ladd() {
        super(MNEMONIC, OPCODE);
    }
}

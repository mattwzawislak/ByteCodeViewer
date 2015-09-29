package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class laload extends Instruction {

    private static final String MNEMONIC = "laload";
    private static final int    OPCODE   = 0x2f;

    public laload() {
        super(MNEMONIC, OPCODE);
    }
}

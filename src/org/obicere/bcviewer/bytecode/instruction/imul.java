package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class imul extends Instruction {

    private static final String MNEMONIC = "imul";
    private static final int    OPCODE   = 0x74;

    public imul() {
        super(MNEMONIC, OPCODE);
    }
}

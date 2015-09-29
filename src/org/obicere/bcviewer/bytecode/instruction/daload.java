package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class daload extends Instruction {

    private static final String MNEMONIC = "daload";
    private static final int    OPCODE   = 0x31;

    public daload() {
        super(MNEMONIC, OPCODE);
    }
}

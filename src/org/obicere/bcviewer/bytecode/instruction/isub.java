package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class isub extends Instruction {

    private static final String MNEMONIC = "isub";
    private static final int    OPCODE   = 0x64;

    public isub() {
        super(MNEMONIC, OPCODE);
    }
}

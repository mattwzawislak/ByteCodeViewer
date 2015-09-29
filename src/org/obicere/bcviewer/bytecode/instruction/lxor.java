package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class lxor extends Instruction {

    private static final String MNEMONIC = "lxor";
    private static final int    OPCODE   = 0x83;

    public lxor() {
        super(MNEMONIC, OPCODE);
    }
}

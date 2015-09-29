package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class dup extends Instruction {

    private static final String MNEMONIC = "dup";
    private static final int    OPCODE   = 0x59;

    public dup() {
        super(MNEMONIC, OPCODE);
    }
}

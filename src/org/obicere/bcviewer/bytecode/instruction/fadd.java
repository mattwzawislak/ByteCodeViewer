package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class fadd extends Instruction {

    private static final String MNEMONIC = "fadd";
    private static final int    OPCODE   = 0x62;

    public fadd() {
        super(MNEMONIC, OPCODE);
    }
}

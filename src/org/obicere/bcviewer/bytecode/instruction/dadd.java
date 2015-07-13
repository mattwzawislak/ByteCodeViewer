package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class dadd extends Instruction {

    private static final String MNEMONIC = "dadd";
    private static final int    OPCODE   = 0x63;

    public dadd() {
        super(MNEMONIC, OPCODE);
    }

}

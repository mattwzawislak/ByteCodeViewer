package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class fastore extends Instruction {

    private static final String MNEMONIC = "fastore";
    private static final int    OPCODE   = 0x51;

    public fastore() {
        super(MNEMONIC, OPCODE);
    }
}

package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class aastore extends Instruction {

    private static final String MNEMONIC = "aastore";
    private static final int    OPCODE   = 0x53;

    public aastore() {
        super(MNEMONIC, OPCODE);
    }

}

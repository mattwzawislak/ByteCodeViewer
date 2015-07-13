package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class sastore extends Instruction {

    private static final String MNEMONIC = "sastore";
    private static final int    OPCODE   = 0x56;

    public sastore() {
        super(MNEMONIC, OPCODE);
    }
}

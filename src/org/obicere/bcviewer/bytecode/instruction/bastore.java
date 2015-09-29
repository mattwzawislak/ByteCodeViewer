package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class bastore extends Instruction {

    private static final String MNEMONIC = "bastore";
    private static final int    OPCODE   = 0x54;

    public bastore() {
        super(MNEMONIC, OPCODE);
    }
}

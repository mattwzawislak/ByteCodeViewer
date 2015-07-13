package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class iand extends Instruction {

    private static final String MNEMONIC = "iand";
    private static final int    OPCODE   = 0x7e;

    public iand() {
        super(MNEMONIC, OPCODE);
    }
}

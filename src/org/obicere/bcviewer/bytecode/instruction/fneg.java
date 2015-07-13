package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class fneg extends Instruction {

    private static final String MNEMONIC = "fneg";
    private static final int    OPCODE   = 0x76;

    public fneg() {
        super(MNEMONIC, OPCODE);
    }
}

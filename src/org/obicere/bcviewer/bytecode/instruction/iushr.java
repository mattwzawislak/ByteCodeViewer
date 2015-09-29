package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class iushr extends Instruction {

    private static final String MNEMONIC = "iushr";
    private static final int    OPCODE   = 0x7c;

    public iushr() {
        super(MNEMONIC, OPCODE);
    }
}

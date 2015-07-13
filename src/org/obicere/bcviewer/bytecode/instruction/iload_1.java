package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class iload_1 extends Instruction {

    private static final String MNEMONIC = "iload_1";
    private static final int    OPCODE   = 0x1b;

    public iload_1() {
        super(MNEMONIC, OPCODE);
    }
}

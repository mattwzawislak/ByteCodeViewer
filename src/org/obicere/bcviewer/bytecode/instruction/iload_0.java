package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class iload_0 extends Instruction {

    private static final String MNEMONIC = "iload_0";
    private static final int    OPCODE   = 0x1a;

    public iload_0() {
        super(MNEMONIC, OPCODE);
    }
}

package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class iload_3 extends Instruction {

    private static final String MNEMONIC = "iload_3";
    private static final int    OPCODE   = 0x1d;

    public iload_3() {
        super(MNEMONIC, OPCODE);
    }
}

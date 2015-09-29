package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class iload_2 extends Instruction {

    private static final String MNEMONIC = "iload_2";
    private static final int    OPCODE   = 0x1c;

    public iload_2() {
        super(MNEMONIC, OPCODE);
    }
}

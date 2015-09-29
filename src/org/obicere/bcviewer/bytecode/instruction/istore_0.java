package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class istore_0 extends Instruction {

    private static final String MNEMONIC = "istore_0";
    private static final int    OPCODE   = 0x3b;

    public istore_0() {
        super(MNEMONIC, OPCODE);
    }
}

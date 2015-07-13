package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class istore_1 extends Instruction {

    private static final String MNEMONIC = "istore_1";
    private static final int    OPCODE   = 0x3c;

    public istore_1() {
        super(MNEMONIC, OPCODE);
    }
}

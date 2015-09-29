package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class istore_3 extends Instruction {

    private static final String MNEMONIC = "istore_3";
    private static final int    OPCODE   = 0x3e;

    public istore_3() {
        super(MNEMONIC, OPCODE);
    }
}

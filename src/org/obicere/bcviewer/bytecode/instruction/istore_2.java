package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class istore_2 extends Instruction {

    private static final String MNEMONIC = "istore_2";
    private static final int    OPCODE   = 0x3d;

    public istore_2() {
        super(MNEMONIC, OPCODE);
    }
}

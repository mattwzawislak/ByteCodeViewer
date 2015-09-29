package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class dstore_1 extends Instruction {

    private static final String MNEMONIC = "dstore_1";
    private static final int    OPCODE   = 0x48;

    public dstore_1() {
        super(MNEMONIC, OPCODE);
    }
}

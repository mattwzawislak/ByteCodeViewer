package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class dstore_3 extends Instruction {

    private static final String MNEMONIC = "dstore_3";
    private static final int    OPCODE   = 0x4a;

    public dstore_3() {
        super(MNEMONIC, OPCODE);
    }
}

package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class dstore_2 extends Instruction {

    private static final String MNEMONIC = "dstore_2";
    private static final int    OPCODE   = 0x49;

    public dstore_2() {
        super(MNEMONIC, OPCODE);
    }

}

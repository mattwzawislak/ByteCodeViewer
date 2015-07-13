package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class dstore extends Instruction {

    private static final String MNEMONIC = "dstore";
    private static final int    OPCODE   = 0x39;

    public dstore() {
        super(MNEMONIC, OPCODE);
    }

}

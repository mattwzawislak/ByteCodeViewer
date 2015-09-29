package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class dload_1 extends Instruction {

    private static final String MNEMONIC = "dload_1";
    private static final int    OPCODE   = 0x27;

    public dload_1() {
        super(MNEMONIC, OPCODE);
    }
}

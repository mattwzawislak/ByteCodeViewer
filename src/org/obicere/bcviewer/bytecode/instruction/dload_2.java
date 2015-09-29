package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class dload_2 extends Instruction {

    private static final String MNEMONIC = "dload_2";
    private static final int    OPCODE   = 0x28;

    public dload_2() {
        super(MNEMONIC, OPCODE);
    }
}

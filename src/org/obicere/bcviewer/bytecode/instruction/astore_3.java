package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class astore_3 extends Instruction {

    private static final String MNEMONIC = "astore_3";
    private static final int    OPCODE   = 0x4e;

    public astore_3() {
        super(MNEMONIC, OPCODE);
    }
}

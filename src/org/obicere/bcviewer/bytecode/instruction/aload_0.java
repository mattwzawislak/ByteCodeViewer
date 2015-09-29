package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class aload_0 extends Instruction {

    private static final String MNEMONIC = "aload_0";
    private static final int    OPCODE   = 0x2a;

    public aload_0() {
        super(MNEMONIC, OPCODE);
    }
}

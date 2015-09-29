package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class aload_3 extends Instruction {

    private static final String MNEMONIC = "aload_3";
    private static final int    OPCODE   = 0x2d;

    public aload_3() {
        super(MNEMONIC, OPCODE);
    }

}

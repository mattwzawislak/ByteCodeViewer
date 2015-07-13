package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class fcmpl extends Instruction {

    private static final String MNEMONIC = "fcmpl";
    private static final int    OPCODE   = 0x95;

    public fcmpl() {
        super(MNEMONIC, OPCODE);
    }
}

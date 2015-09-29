package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class irem extends Instruction {

    private static final String MNEMONIC = "irem";
    private static final int    OPCODE   = 0x70;

    public irem() {
        super(MNEMONIC, OPCODE);
    }
}

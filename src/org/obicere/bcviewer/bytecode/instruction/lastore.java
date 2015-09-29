package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class lastore extends Instruction {

    private static final String MNEMONIC = "lastore";
    private static final int    OPCODE   = 0x50;

    public lastore() {
        super(MNEMONIC, OPCODE);
    }
}

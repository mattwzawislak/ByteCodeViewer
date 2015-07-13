package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class idiv extends Instruction {

    private static final String MNEMONIC = "idiv";
    private static final int    OPCODE   = 0x6c;

    public idiv() {
        super(MNEMONIC, OPCODE);
    }
}

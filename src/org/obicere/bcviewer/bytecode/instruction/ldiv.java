package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class ldiv extends Instruction {

    private static final String MNEMONIC = "ldiv";
    private static final int    OPCODE   = 0x6d;

    public ldiv() {
        super(MNEMONIC, OPCODE);
    }
}

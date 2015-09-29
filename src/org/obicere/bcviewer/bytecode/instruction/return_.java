package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class return_ extends Instruction {

    private static final String MNEMONIC = "return";
    private static final int    OPCODE   = 0xb1;

    public return_() {
        super(MNEMONIC, OPCODE);
    }
}

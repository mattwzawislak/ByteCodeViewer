package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class d2i extends Instruction {

    private static final String MNEMONIC = "d2i";
    private static final int OPCODE = 0x8e;

    public d2i(){
        super(MNEMONIC, OPCODE);
    }

}

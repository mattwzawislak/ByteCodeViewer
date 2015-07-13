package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class areturn extends Instruction {

    private static final String MNEMONIC = "areturn";
    private static final int OPCODE = 0xb0;

    public areturn(){
        super(MNEMONIC, OPCODE);
    }

}

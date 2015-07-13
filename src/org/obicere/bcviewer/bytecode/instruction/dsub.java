package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class dsub extends Instruction {

    private static final String MNEMONIC = "dsub";
    private static final int OPCODE = 0x67;

    public dsub(){
        super(MNEMONIC, OPCODE);
    }

}

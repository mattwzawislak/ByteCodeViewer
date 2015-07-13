package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class aconst_null extends Instruction {

    private static final String MNEMONIC = "aconst_null";
    private static final int OPCODE = 0x01;

    public aconst_null(){
        super(MNEMONIC, OPCODE);
    }

}

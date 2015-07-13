package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class astore_1 extends Instruction {

    private static final String MNEMONIC = "astore_1";
    private static final int OPCODE = 0x4c;

    public astore_1(){
        super(MNEMONIC, OPCODE);
    }

}

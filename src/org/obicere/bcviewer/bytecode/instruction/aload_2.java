package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class aload_2 extends Instruction {

    private static final String MNEMONIC = "aload_2";
    private static final int OPCODE = 0x2c;

    public aload_2(){
        super(MNEMONIC, OPCODE);
    }

}

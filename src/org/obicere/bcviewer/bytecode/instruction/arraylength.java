package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class arraylength extends Instruction {

    private static final String MNEMONIC = "arraylength";
    private static final int OPCODE = 0xbe;

    public arraylength(){
        super(MNEMONIC, OPCODE);
    }

}

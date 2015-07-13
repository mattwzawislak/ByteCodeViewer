package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class aaload extends Instruction {

    private static final String MNEMONIC = "aaload";
    private static final int OPCODE = 0x32;

    public aaload(){
        super(MNEMONIC, OPCODE);
    }

}

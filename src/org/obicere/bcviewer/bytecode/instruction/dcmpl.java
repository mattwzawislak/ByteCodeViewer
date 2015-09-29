package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class dcmpl extends Instruction {

    private static final String MNEMONIC = "dcmpl";
    private static final int    OPCODE   = 0x97;

    public dcmpl() {
        super(MNEMONIC, OPCODE);
    }
}

package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class lstore extends Instruction {

    private static final String MNEMONIC = "lstore";
    private static final int    OPCODE   = 0x37;

    private final int index;

    public lstore(final int index) {
        super(MNEMONIC, OPCODE);
        this.index = index;
    }

    public int getIndex(){
        return index;
    }
}

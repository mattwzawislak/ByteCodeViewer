package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class dstore extends Instruction {

    private static final String MNEMONIC = "dstore";
    private static final int    OPCODE   = 0x39;

    private final int index;

    public dstore(final int index) {
        super(MNEMONIC, OPCODE);
        this.index = index;
    }

    public int getIndex(){
        return index;
    }

}

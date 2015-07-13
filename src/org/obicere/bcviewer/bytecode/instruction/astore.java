package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class astore extends Instruction {

    private static final String MNEMONIC = "astore";
    private static final int    OPCODE   = 0x3a;

    private final int index;

    public astore(final int index) {
        super(MNEMONIC, OPCODE);
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

}

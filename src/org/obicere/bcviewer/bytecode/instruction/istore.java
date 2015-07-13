package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class istore extends Instruction {

    private static final String MNEMONIC = "istore";
    private static final int    OPCODE   = 0x36;

    private final int index;

    public istore(final int index) {
        super(MNEMONIC, OPCODE);
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}

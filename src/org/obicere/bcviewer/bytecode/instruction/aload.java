package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class aload extends Instruction {

    private static final String MNEMONIC = "aload";
    private static final int    OPCODE   = 0x19;

    private final int index;

    public aload(final int index) {
        super(MNEMONIC, OPCODE);
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

}

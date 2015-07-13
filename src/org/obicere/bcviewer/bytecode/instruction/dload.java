package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class dload extends Instruction {

    private static final String MNEMONIC = "dload";
    private static final int    OPCODE   = 0x18;

    private final int index;

    public dload(final int index) {
        super(MNEMONIC, OPCODE);
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

}

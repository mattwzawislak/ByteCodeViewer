package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class iinc extends Instruction {

    private static final String MNEMONIC = "iinc";
    private static final int    OPCODE   = 0x84;

    private final int index;
    private final int constant;

    public iinc(final int index, final int constant) {
        super(MNEMONIC, OPCODE);
        this.index = index;
        this.constant = constant;
    }

    public int getIndex() {
        return index;
    }

    public int getConstant() {
        return constant;
    }
}

package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class newarray extends Instruction {

    private static final String MNEMONIC = "newarray";
    private static final int    OPCODE   = 0xbc;

    private final int atype;

    public newarray(final int atype) {
        super(MNEMONIC, OPCODE);
        this.atype = atype;
    }

    public int getAtype() {
        return atype;
    }
}

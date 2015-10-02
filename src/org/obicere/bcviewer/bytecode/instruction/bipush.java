package org.obicere.bcviewer.bytecode.instruction;

import org.obicere.bcviewer.dom.DocumentBuilder;

/**
 * @author Obicere
 */
public class bipush extends Instruction {

    private static final String MNEMONIC = "bipush";
    private static final int    OPCODE   = 0x10;

    private final int value;

    public bipush(final int value) {
        super(MNEMONIC, OPCODE);
        this.value = value;
    }

    public int getByte() {
        return value;
    }

    @Override
    public void model(final DocumentBuilder builder) {
        super.model(builder);
        builder.tab();
        builder.add((byte) value);
    }
}

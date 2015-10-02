package org.obicere.bcviewer.bytecode.instruction;

import org.obicere.bcviewer.bytecode.ConstantPool;
import org.obicere.bcviewer.dom.DocumentBuilder;

/**
 * @author Obicere
 */
public class invokespecial extends Instruction {

    private static final String MNEMONIC = "invokespecial";
    private static final int    OPCODE   = 0xb7;

    private final int indexbyte1;
    private final int indexbyte2;

    public invokespecial(final int indexbyte1, final int indexbyte2) {
        super(MNEMONIC, OPCODE);
        this.indexbyte1 = indexbyte1;
        this.indexbyte2 = indexbyte2;
    }

    public int getIndexbyte1() {
        return indexbyte1;
    }

    public int getIndexbyte2() {
        return indexbyte2;
    }

    public int getIndex() {
        return (indexbyte1 << 8) | indexbyte2;
    }

    @Override
    public String toString(final ConstantPool constantPool) {
        final StringBuilder builder = new StringBuilder(MNEMONIC);
        builder.append(' ');
        builder.append(constantPool.getAsString(getIndex()));
        return builder.toString();
    }

    @Override
    public void model(final DocumentBuilder builder) {
        super.model(builder);
        builder.getConstantPool().get(getIndex()).modelValue(builder);
    }
}

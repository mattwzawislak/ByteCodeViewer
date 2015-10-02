package org.obicere.bcviewer.bytecode.instruction;

import org.obicere.bcviewer.bytecode.ConstantPool;
import org.obicere.bcviewer.dom.DocumentBuilder;

/**
 * @author Obicere
 */
public class invokeinterface extends Instruction {

    private static final String MNEMONIC = "invokeinterface";
    private static final int    OPCODE   = 0xb9;

    private final int indexbyte1;
    private final int indexbyte2;
    private final int count;

    public invokeinterface(final int indexbyte1, final int indexbyte2, final int count, final int indexbyte4) {
        super(MNEMONIC, OPCODE);
        if (count == 0) {
            throw new ClassFormatError("count operand must not be 0.");
        }
        if (indexbyte4 != 0) {
            throw new ClassFormatError("invokeinerface byte 4 must be set to 0.");
        }
        this.indexbyte1 = indexbyte1;
        this.indexbyte2 = indexbyte2;
        this.count = count;
    }

    public int getIndexbyte1() {
        return indexbyte1;
    }

    public int getIndexbyte2() {
        return indexbyte2;
    }

    public int getCount() {
        return count;
    }

    public int getIndex() {
        return (indexbyte1 << 8) | indexbyte2;
    }

    @Override
    public String toString(final ConstantPool constantPool) {
        final StringBuilder builder = new StringBuilder(MNEMONIC);
        builder.append(' ');
        builder.append(constantPool.getAsString(getIndex()));
        builder.append(' ');
        builder.append(count);
        builder.append(" 0"); // always a 0
        return builder.toString();
    }

    @Override
    public void model(final DocumentBuilder builder) {
        super.model(builder);
        builder.tab();
        builder.getConstantPool().get(getIndex()).modelValue(builder);
        builder.tab();
        builder.add(count);
        builder.tab();
        builder.add(0);
    }
}

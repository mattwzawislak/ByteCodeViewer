package org.obicere.bcviewer.bytecode.instruction;

import org.obicere.bcviewer.bytecode.ConstantPool;
import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;

import javax.swing.text.Element;

/**
 * @author Obicere
 */
public class invokedynamic extends Instruction {

    private static final String MNEMONIC = "invokedynamic";
    private static final int    OPCODE   = 0xba;

    private final int indexbyte1;
    private final int indexbyte2;

    public invokedynamic(final int indexbyte1, final int indexbyte2, final int indexbyte3, final int indexbyte4) {
        super(MNEMONIC, OPCODE);
        if (indexbyte3 != 0 || indexbyte4 != 0) {
            throw new ClassFormatError("invokedynamic bytes 3 and 4 must be set to 0.");
        }
        this.indexbyte1 = indexbyte1;
        this.indexbyte2 = indexbyte2;
    }

    public int getIndexbyte1() {
        return indexbyte1;
    }

    public int getIndexbyte2() {
        return indexbyte2;
    }

    public int getIndex(){
        return (indexbyte1 << 8) | indexbyte2;
    }

    @Override
    public String toString(final ConstantPool constantPool) {
        final StringBuilder builder = new StringBuilder(MNEMONIC);
        builder.append(' ');
        builder.append(constantPool.getAsString(getIndex()));
        builder.append(" 0 0"); // always 0
        return builder.toString();
    }

    @Override
    public void model(final BytecodeDocumentBuilder builder, final Element parent) {
        super.model(builder, parent);
        builder.tab();
        builder.getConstantPool().get(getIndex()).modelValue(builder, parent);
        //builder.addPlain(builder.getConstantPool().getAsString(getIndex()));
        builder.tab();
        builder.add(0);
        builder.tab();
        builder.add(0);
    }
}

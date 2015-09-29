package org.obicere.bcviewer.bytecode.instruction;

import org.obicere.bcviewer.bytecode.ConstantPool;
import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;
import org.obicere.bcviewer.dom.Modeler;

import javax.swing.text.Element;

/**
 * @author Obicere
 */
public class multianewarray extends Instruction implements Modeler {

    private static final String MNEMONIC = "multianewarray";
    private static final int    OPCODE   = 0xc5;

    private final int indexbyte1;
    private final int indexbyte2;
    private final int dimensions;

    public multianewarray(final int indexbyte1, final int indexbyte2, final int dimensions) {
        super(MNEMONIC, OPCODE);
        this.indexbyte1 = indexbyte1;
        this.indexbyte2 = indexbyte2;
        this.dimensions = dimensions;
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

    public int getDimensions() {
        return dimensions;
    }

    @Override
    public String toString(final ConstantPool constantPool) {
        final StringBuilder builder = new StringBuilder(MNEMONIC);
        builder.append(' ');
        builder.append(constantPool.getAsString(getIndex()));
        builder.append(' ');
        builder.append(dimensions);
        return builder.toString();
    }

    @Override
    public void model(final BytecodeDocumentBuilder builder, final Element parent) {
        super.model(builder, parent);
        builder.tab(parent);
        builder.add(parent, getIndex());
        builder.tab(parent);
        builder.add(parent, dimensions);
    }
}

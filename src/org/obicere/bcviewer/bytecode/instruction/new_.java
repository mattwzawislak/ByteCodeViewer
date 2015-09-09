package org.obicere.bcviewer.bytecode.instruction;

import org.obicere.bcviewer.bytecode.ConstantPool;
import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.Element;
import org.obicere.bcviewer.dom.Modeler;
import org.obicere.bcviewer.dom.bytecode.InstructionElement;
import org.obicere.bcviewer.dom.literals.ParameterStringElement;

/**
 * @author Obicere
 */
public class new_ extends Instruction implements Modeler<new_> {

    private static final String MNEMONIC = "new";
    private static final int    OPCODE   = 0xbb;

    private final int indexbyte1;
    private final int indexbyte2;

    public new_(final int indexbyte1, final int indexbyte2) {
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
        builder.append(constantPool.getAsCodeString(getIndex()));
        return builder.toString();
    }

    @Override
    public void model(final DocumentBuilder builder, final Element parent) {
        parent.add(new InstructionElement(this, builder));
        parent.add(new ParameterStringElement("index", builder.getConstantPool().getAsString(getIndex()), builder));
    }
}

package org.obicere.bcviewer.bytecode.instruction;

import org.obicere.bcviewer.bytecode.ConstantPool;
import org.obicere.bcviewer.dom.DecimalElement;
import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.Element;
import org.obicere.bcviewer.dom.FontResourcePool;
import org.obicere.bcviewer.dom.IntegerElement;
import org.obicere.bcviewer.dom.Modeler;
import org.obicere.bcviewer.dom.bytecode.InstructionElement;

/**
 * @author Obicere
 */
public class multianewarray extends Instruction implements Modeler<multianewarray> {

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
        builder.append(constantPool.getAsCodeString(getIndex()));
        builder.append(' ');
        builder.append(dimensions);
        return builder.toString();
    }

    @Override
    public void model(final DocumentBuilder builder, final Element parent) {
        parent.add(new InstructionElement(this, builder));

        final IntegerElement element1 = new DecimalElement("indexbyte1", indexbyte1);
        element1.setLeftPad(builder.getTabbedPaddingSize(element1.getText().length(), builder.getTabSize()));
        element1.getAttributes().setFont(builder.getFontPool().get(FontResourcePool.FONT_BASELINE_PLAIN));

        final IntegerElement element2 = new DecimalElement("indexbyte2", indexbyte2);
        element2.setLeftPad(builder.getTabbedPaddingSize(element2.getText().length(), builder.getTabSize()));
        element2.getAttributes().setFont(builder.getFontPool().get(FontResourcePool.FONT_BASELINE_PLAIN));

        final IntegerElement element3 = new DecimalElement("dimensions", dimensions);
        element3.setLeftPad(builder.getTabbedPaddingSize(element3.getText().length(), builder.getTabSize()));
        element3.getAttributes().setFont(builder.getFontPool().get(FontResourcePool.FONT_BASELINE_PLAIN));

        parent.add(element1);
        parent.add(element2);
        parent.add(element3);
    }
}

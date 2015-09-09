package org.obicere.bcviewer.bytecode.instruction;

import org.obicere.bcviewer.dom.DecimalElement;
import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.Element;
import org.obicere.bcviewer.dom.FontResourcePool;
import org.obicere.bcviewer.dom.IntegerElement;
import org.obicere.bcviewer.dom.Modeler;
import org.obicere.bcviewer.dom.bytecode.InstructionElement;
import org.obicere.bcviewer.dom.literals.ParameterDecimalElement;

/**
 * @author Obicere
 */
public class aload extends Instruction implements Modeler<aload> {

    private static final String MNEMONIC = "aload";
    private static final int    OPCODE   = 0x19;

    private final int index;

    public aload(final int index) {
        super(MNEMONIC, OPCODE);
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public void model(final DocumentBuilder builder, final Element parent) {
        parent.add(new InstructionElement(this, builder));
        parent.add(new ParameterDecimalElement("index", index, builder));
    }

}

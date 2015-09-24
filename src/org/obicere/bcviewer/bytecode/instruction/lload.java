package org.obicere.bcviewer.bytecode.instruction;

import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.Element;
import org.obicere.bcviewer.dom.bytecode.InstructionElement;
import org.obicere.bcviewer.dom.literals.ParameterIntegerElement;

/**
 * @author Obicere
 */
public class lload extends Instruction {

    private static final String MNEMONIC = "lload";
    private static final int    OPCODE   = 0x16;

    private final int index;

    public lload(final int index) {
        super(MNEMONIC, OPCODE);
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public void model(final DocumentBuilder builder, final Element parent) {
        parent.add(new InstructionElement(this, builder));
        parent.add(new ParameterIntegerElement("index", index, builder));
    }
}

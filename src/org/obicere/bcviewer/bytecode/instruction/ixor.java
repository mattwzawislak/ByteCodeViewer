package org.obicere.bcviewer.bytecode.instruction;

import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.Element;
import org.obicere.bcviewer.dom.bytecode.InstructionElement;

/**
 * @author Obicere
 */
public class ixor extends Instruction {

    private static final String MNEMONIC = "ixor";
    private static final int    OPCODE   = 0x82;

    public ixor() {
        super(MNEMONIC, OPCODE);
    }

    @Override
    public void model(final DocumentBuilder builder, final Element parent) {
        parent.add(new InstructionElement(this, builder));
    }
}

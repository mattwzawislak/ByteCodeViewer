package org.obicere.bcviewer.bytecode.instruction;

import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.Element;
import org.obicere.bcviewer.dom.bytecode.InstructionElement;

/**
 * @author Obicere
 */
public class irem extends Instruction {

    private static final String MNEMONIC = "irem";
    private static final int    OPCODE   = 0x70;

    public irem() {
        super(MNEMONIC, OPCODE);
    }

    @Override
    public void model(final DocumentBuilder builder, final Element parent) {
        parent.add(new InstructionElement(this, builder));
    }
}

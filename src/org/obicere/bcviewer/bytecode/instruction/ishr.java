package org.obicere.bcviewer.bytecode.instruction;

import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.Element;
import org.obicere.bcviewer.dom.bytecode.InstructionElement;

/**
 * @author Obicere
 */
public class ishr extends Instruction {

    private static final String MNEMONIC = "ishr";
    private static final int    OPCODE   = 0x7a;

    public ishr() {
        super(MNEMONIC, OPCODE);
    }

    @Override
    public void model(final DocumentBuilder builder, final Element parent) {
        parent.add(new InstructionElement(this, builder));
    }
}

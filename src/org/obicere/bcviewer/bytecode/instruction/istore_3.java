package org.obicere.bcviewer.bytecode.instruction;

import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.Element;
import org.obicere.bcviewer.dom.bytecode.InstructionElement;

/**
 * @author Obicere
 */
public class istore_3 extends Instruction {

    private static final String MNEMONIC = "istore_3";
    private static final int    OPCODE   = 0x3e;

    public istore_3() {
        super(MNEMONIC, OPCODE);
    }

    @Override
    public void model(final DocumentBuilder builder, final Element parent) {
        parent.add(new InstructionElement(this, builder));
    }
}

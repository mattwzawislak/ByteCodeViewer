package org.obicere.bcviewer.bytecode.instruction;

import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.Element;
import org.obicere.bcviewer.dom.bytecode.InstructionElement;
import org.obicere.bcviewer.dom.literals.ParameterIntegerElement;

/**
 * @author Obicere
 */
public class iload_0 extends Instruction {

    private static final String MNEMONIC = "iload_0";
    private static final int    OPCODE   = 0x1a;

    public iload_0() {
        super(MNEMONIC, OPCODE);
    }

    @Override
    public void model(final DocumentBuilder builder, final Element parent) {
        parent.add(new InstructionElement(this, builder));
    }
}

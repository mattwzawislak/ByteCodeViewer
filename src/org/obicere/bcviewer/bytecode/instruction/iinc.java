package org.obicere.bcviewer.bytecode.instruction;

import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.Element;
import org.obicere.bcviewer.dom.bytecode.InstructionElement;
import org.obicere.bcviewer.dom.literals.ParameterIntegerElement;

/**
 * @author Obicere
 */
public class iinc extends Instruction {

    private static final String MNEMONIC = "iinc";
    private static final int    OPCODE   = 0x84;

    private final int index;
    private final int constant;

    public iinc(final int index, final int constant) {
        super(MNEMONIC, OPCODE);
        this.index = index;
        this.constant = constant;
    }

    public int getIndex() {
        return index;
    }

    public int getConstant() {
        return constant;
    }

    @Override
    public void model(final DocumentBuilder builder, final Element parent) {
        parent.add(new InstructionElement(this, builder));
        parent.add(new ParameterIntegerElement("index", index, builder));
        parent.add(new ParameterIntegerElement("constant", constant, builder));
    }
}

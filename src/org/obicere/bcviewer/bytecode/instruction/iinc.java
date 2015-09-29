package org.obicere.bcviewer.bytecode.instruction;

import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;

import javax.swing.text.Element;

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
    public void model(final BytecodeDocumentBuilder builder, final Element parent) {
        super.model(builder, parent);
        builder.tab(parent);
        builder.add(parent, index);
        builder.tab(parent);
        builder.add(parent, constant);
    }
}

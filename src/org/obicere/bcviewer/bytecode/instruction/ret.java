package org.obicere.bcviewer.bytecode.instruction;

import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;

import javax.swing.text.Element;

/**
 * @author Obicere
 */
public class ret extends Instruction {

    private static final String MNEMONIC = "ret";
    private static final int    OPCODE   = 0xa9;

    private final int index;

    public ret(final int index) {
        super(MNEMONIC, OPCODE);
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public void model(final BytecodeDocumentBuilder builder, final Element parent) {
        super.model(builder, parent);
        builder.tab();
        builder.add(index);
    }
}

package org.obicere.bcviewer.bytecode.instruction;

import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;

import javax.swing.text.Element;

/**
 * @author Obicere
 */
public class iload extends Instruction {

    private static final String MNEMONIC = "iload";
    private static final int    OPCODE   = 0x15;

    private final int index;

    public iload(final int index) {
        super(MNEMONIC, OPCODE);
        this.index = index;
    }

    public int getIndex(){
        return index;
    }

    @Override
    public void model(final BytecodeDocumentBuilder builder, final Element parent) {
        super.model(builder, parent);
        builder.tab();
        builder.add(index);
    }
}

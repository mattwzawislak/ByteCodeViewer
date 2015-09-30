package org.obicere.bcviewer.bytecode.instruction;

import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;
import org.obicere.bcviewer.dom.Modeler;

import javax.swing.text.Element;

/**
 * @author Obicere
 */
public class aload extends Instruction implements Modeler {

    private static final String MNEMONIC = "aload";
    private static final int    OPCODE   = 0x19;

    private final int index;

    public aload(final int index) {
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

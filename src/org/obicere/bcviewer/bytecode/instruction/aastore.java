package org.obicere.bcviewer.bytecode.instruction;

import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.Element;
import org.obicere.bcviewer.dom.Modeler;
import org.obicere.bcviewer.dom.bytecode.InstructionElement;

/**
 * @author Obicere
 */
public class aastore extends Instruction implements Modeler<aastore> {

    private static final String MNEMONIC = "aastore";
    private static final int    OPCODE   = 0x53;

    public aastore() {
        super(MNEMONIC, OPCODE);
    }

    @Override
    public void model(final DocumentBuilder builder, final Element parent) {
        parent.add(new InstructionElement(this, builder));
    }
}

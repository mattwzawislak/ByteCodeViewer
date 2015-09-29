package org.obicere.bcviewer.bytecode.instruction;

import org.obicere.bcviewer.dom.Modeler;

/**
 * @author Obicere
 */
public class aastore extends Instruction implements Modeler {

    private static final String MNEMONIC = "aastore";
    private static final int    OPCODE   = 0x53;

    public aastore() {
        super(MNEMONIC, OPCODE);
    }
}

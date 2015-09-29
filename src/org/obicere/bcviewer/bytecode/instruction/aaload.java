package org.obicere.bcviewer.bytecode.instruction;

import org.obicere.bcviewer.dom.Modeler;

/**
 * @author Obicere
 */
public class aaload extends Instruction implements Modeler {

    private static final String MNEMONIC = "aaload";
    private static final int    OPCODE   = 0x32;

    public aaload() {
        super(MNEMONIC, OPCODE);
    }
}

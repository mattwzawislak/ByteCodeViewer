package org.obicere.bcviewer.bytecode.instruction;

import org.obicere.bcviewer.dom.Modeler;

/**
 * @author Obicere
 */
public class aconst_null extends Instruction implements Modeler {

    private static final String MNEMONIC = "aconst_null";
    private static final int    OPCODE   = 0x01;

    public aconst_null() {
        super(MNEMONIC, OPCODE);
    }
}

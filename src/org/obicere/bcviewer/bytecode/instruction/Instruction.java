package org.obicere.bcviewer.bytecode.instruction;

import org.obicere.bcviewer.bytecode.BytecodeElement;

/**
 * @author Obicere
 */
public abstract class Instruction extends BytecodeElement {

    private final String mnemonic;

    private final int opcode;

    public Instruction(final String mnemonic, final int opcode) {
        if (mnemonic.length() == 0) { // Will throw NPE if null
            throw new IllegalArgumentException("mnemonic must not be empty.");
        }
        this.mnemonic = mnemonic;
        this.opcode = opcode;
    }

    public final String getMnemonic() {
        return mnemonic;
    }

    public final int getOpcode() {
        return opcode;
    }

    public String getIdentifier() {
        return getClass().getCanonicalName() + getStart();
    }

}

package org.obicere.bcviewer.bytecode.instruction;

import org.obicere.bcviewer.bytecode.BytecodeElement;
import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;

/**
 * @author Obicere
 */
public abstract class Instruction extends BytecodeElement {

    private static final int MAX_NAME_LENGTH = 14;

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

    @Override
    public String getIdentifier() {
        return getClass().getCanonicalName() + getStart();
    }

    @Override
    public void model(final BytecodeDocumentBuilder builder) {
        builder.addKeyword(mnemonic);
        builder.padTabbed(MAX_NAME_LENGTH, mnemonic.length());
    }

}

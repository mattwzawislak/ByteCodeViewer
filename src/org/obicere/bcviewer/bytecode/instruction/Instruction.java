package org.obicere.bcviewer.bytecode.instruction;

import org.obicere.bcviewer.bytecode.BytecodeElement;
import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.Element;
import org.obicere.bcviewer.dom.literals.PlainElement;

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

    @Override
    public String getIdentifier() {
        return getClass().getCanonicalName() + getStart();
    }

    @Override
    public void model(final DocumentBuilder builder, final Element parent) {
        parent.add(new PlainElement("temp", getIdentifier(), builder));
    }

}

package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public abstract class VerificationTypeInfo extends BytecodeElement {

    private final int tag;

    public VerificationTypeInfo(final int tag) {
        this.tag = tag;
    }

    public int getTag() {
        return tag;
    }

    @Override
    public String getIdentifier() {
        return "verificationTypeInfo" + getStart();
    }
}

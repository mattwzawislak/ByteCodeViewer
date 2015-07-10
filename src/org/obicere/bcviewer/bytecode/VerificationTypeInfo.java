package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class VerificationTypeInfo {

    private final int tag;
    private final int index;

    public VerificationTypeInfo(final int tag) {
        this.tag = tag;
        this.index = -1;
    }

    public VerificationTypeInfo(final int tag, final int index) {
        this.tag = tag;
        this.index = index;
    }
}

package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class SignatureAttribute extends Attribute {

    private final int signatureIndex;

    public SignatureAttribute(final int signatureIndex) {

        this.signatureIndex = signatureIndex;
    }

    public int getSignatureIndex(){
        return signatureIndex;
    }
}
